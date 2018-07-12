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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI;

@Controller
@SpringBootApplication
public class Main {
  private Map<String, String> sessions = new HashMap<String, String>();

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
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
  String login(@RequestBody Json data, HttpServletResponse response){
    System.out.println("################");
    System.out.println(data);
    /*
    if (data == null || data == ""){
      throw new IllegalArgumentException("The 'name' parameter must not be null or empty");
    }

    String[] split = data.split("|");
    if (split.length != 2){
      throw new IllegalArgumentException("The parameter must be well formed");
    }
    if (!sessions.containsKey(split[0])){
      sessions.put(split[0], split[1]);
      System.out.println("request was successful");
      return "single";
    } else{
*/
      return "index";
  //  }
  }

  @RequestMapping("/logout")
  String logout(@RequestBody String data){
    if (data == null){
      return "error";
    }

    String[] split = data.split("|");

    if (split.length != 2){
      return "error";
    }

    if (!sessions.containsKey(split[0]) || sessions.get(split[0]) != split[1]){
      return "error";
    }

    sessions.remove(split[0]);

    return "index";
  }
}