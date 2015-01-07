package com.cpsc310.sc2.server.parser;

public class Triple<T, U, V>
{
  T x;
  U y;
  V z;

  Triple(T a, U b, V c)
  {
   this.x = a;
   this.y = b;
   this.z = c;
  }

  T getX(){ return x;}
  U getY(){ return y;}
  V getZ(){ return z;}
}

//credit for this classes code: http://stackoverflow.com/questions/6010843/java-how-to-store-data-triple-in-a-list
//accessed March 2nd 2013