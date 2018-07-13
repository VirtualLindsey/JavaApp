/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cfpb;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI;

@Controller
@SpringBootApplication
public class Main {
  private ArrayList<String> sessions = new ArrayList<String>();

  @Autowired
  private DispatcherServlet servlet;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @Bean
  public CommandLineRunner getCommandLineRunner(ApplicationContext context) {
    servlet.setThrowExceptionIfNoHandlerFound(true);
    return args -> {};
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }

  @RequestMapping("/single")
  String single(@RequestParam(name="name", required=false, defaultValue="single") String name, Model model) {
    model.addAttribute("name", name);
    return "single";
  }

  @RequestMapping("/compare")
  String compare(@RequestParam(name="name", required=false, defaultValue="compare") String name, Model model) {
    model.addAttribute("name", name);
    return "compare";
  }


  @RequestMapping(value = "/login")
  String login(@RequestBody String data, HttpServletResponse response){


    if (data == null || data == ""){
      throw new IllegalArgumentException("The 'name' parameter must not be null or empty");
    }

    if (!sessions.contains(data)){
      sessions.add(data);
      System.out.println("returning single page?");
      return "single";
    } else {
      System.out.println("returing index");
      return "index";
    }
  }

  @RequestMapping("/logout")
  String logout(@RequestBody String data){
    if (data == null || data == ""){
      throw new IllegalArgumentException("The 'name' parameter must not be null or empty");
    }


    if (!sessions.contains(data)){
      throw new IllegalArgumentException("Must have a valid session");
    }

    sessions.remove(data);

    return "index";
  }
}