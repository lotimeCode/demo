package demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @author wangzhimin
 * @version create 2018/10/24 9:35
 */
public class TestDemo1 {

	public static void main(String[] args) {

		test2();

		Integer ins = null;
		if (ins == 0) {

		}
	}

	public static void test2(){
		SortedSet<Integer> sortedSet = new TreeSet<>();
		sortedSet.add(3);
		sortedSet.add(4);
		sortedSet.add(5);
		sortedSet.add(1);
		sortedSet.add(2);

		System.out.println(sortedSet.toString());
		System.out.println(sortedSet.headSet(3));
		System.out.println(sortedSet.tailSet(3));
		System.out.println(sortedSet.last());
	}


	public static void test1(){
		//		RangeMap<Integer, String> ipRangeMapCacheTmp = TreeRangeMap.create();
//		ipRangeMapCacheTmp.put(Range.closed(1, 10), "aa");
//		ipRangeMapCacheTmp.put(Range.closed(3, 8), "bb");
//
//		Object o = ipRangeMapCacheTmp.asMapOfRanges();
//		System.out.println(ipRangeMapCacheTmp);
//		System.out.println(ipRangeMapCacheTmp.get(4));
//
//
//		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<5;i++){
//			sb.append(i).append(",");
//		}

		String a = "11,22,33,11";
		List<String> list = new ArrayList<>(Arrays.asList(a.split(",")));
		list.remove("22");

		System.out.println(list);

		System.out.println(list.contains(Integer.toString(11)));


		Set<String> set = new HashSet<>(Arrays.asList(a.split(",")));
		System.out.println(new ArrayList<>(set));

		set.remove("22");
		System.out.println(set);

		System.out.println(new Random().nextInt(100));


		String str1 = new String("abc").intern();
		String str2 = new String("abc").intern();
		System.out.println(str1 == str2);

		List<Integer> list1 = new ArrayList<>();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		list1.add(5);

		List<Integer> list2 = new ArrayList<>();
//		list2.add(2);
//		list2.add(3);
//		list2.add(4);

		System.out.println(list2.removeAll(list1));

//		list2.removeAll(new ArrayList<Integer>());
		System.out.println(null instanceof String);
	}

}
