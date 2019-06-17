package com.follow.user.controller;


import com.follow.user.api.UserServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserComsumerController {

  @Autowired
  private UserServiceApi userServiceApi;

   // private static final String Rest_Url_Prefix="http://localhost:8001";
   // private static final String Rest_Url_Prefix="http://MICROSESPRINGCLOUD-USER";

  /*  @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/comsumer/selectList",method = RequestMethod.GET)
    public List<User> selectList(){
       // QueryWrapper qw = new QueryWrapper();
        return restTemplate.getForObject(Rest_Url_Prefix+"/selectList",List.class);
    }
    @RequestMapping(value="/comsumer/insertuser",method =RequestMethod.POST)
    public boolean insertUser(@RequestBody User user){
    return restTemplate.postForObject(Rest_Url_Prefix+"/insertuser/",user, Boolean.class);
    }

    @RequestMapping(value="/comsumer/getOneUser/{id}",method = RequestMethod.GET)
    public User getOneUser(@PathVariable String id ){
        return restTemplate.getForObject(Rest_Url_Prefix+"/getOneUser/"+id,User.class);
    }*/

/*     @RequestMapping(value = "/comsumer/selectList")
     public List<User> selectList(){
      // QueryWrapper qw = new QueryWrapper();
      return this.userServiceApi.selectList();
     }
     @RequestMapping(value="/comsumer/insertuser")
     public boolean insertUser(@RequestBody User user){

         return this.userServiceApi.insertUser(user);
     }

     @RequestMapping(value="/comsumer/getOneUser/{id}")
     public User getOneUser(@PathVariable("id") String id ){
         System.out.println("id="+id);
         return this.userServiceApi.getOneUser(id);
     }*/

}
