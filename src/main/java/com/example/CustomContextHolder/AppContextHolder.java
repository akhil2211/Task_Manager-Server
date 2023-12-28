package com.example.CustomContextHolder;

import com.example.Model.User;

import java.util.HashMap;
import java.util.Map;

public class AppContextHolder {

       private static String ID= "id";

        private static ThreadLocal<Map<String,Object>> contextHolder = ThreadLocal.withInitial(HashMap::new);

        public static Integer getUserId() {
          Map<String,Object> appcontext=contextHolder.get();
            return (Integer) appcontext.get(ID);
        }
        public static void setUserId(Integer userId) {
            Map<String,Object> appcontext=contextHolder.get();
            appcontext.put(ID,userId);
        }
        public static void clearContext() {
            contextHolder.remove();
        }
    }
