import java.util.Arrays;

class Solution {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if(m==0){
            nums1[0]=nums2[0];
        }
        for(int i=0;i<n;i++){
            nums1[m]=nums2[i];
            m++;
        }
        Arrays.sort(nums1);
    }

}