package com.demo.test;

class Solution {
    public static boolean isPalindrome(int x) {
        String str=Integer.toString(x);
        StringBuilder sb = new StringBuilder(str);

        if(sb.reverse().toString().equals(str)){
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(121));

    }
}
