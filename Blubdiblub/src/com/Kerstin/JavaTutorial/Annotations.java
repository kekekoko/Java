package com.Kerstin.JavaTutorial;

@interface EnhancementRequest {
   int id();
   String synopsis();
   String engineer() default "unassigned";
   String date() default "unknown";
}