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
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import java.io.FileReader;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI;

@Controller
@SpringBootApplication
public class Main {

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


  @PostMapping("/login")
  String login(@RequestParam(name="data", required=true) String data){
    System.out.println(data);
    return "index";
  }
  /*
  @RequestMapping("/logout")
  void logout(@RequestParam(name="token", required=true) String token){
  }*/
}