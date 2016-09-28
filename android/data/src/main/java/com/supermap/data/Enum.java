package com.supermap.data;

/**
 * @author 李云锦
 * @version 2.0
 */
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public abstract class Enum {
    private final int m_value;
    private final int m_ugcValue;
    
    private static boolean m_isCustom;
    
    protected static HashMap<Class<?>, ArrayList<Enum>> m_hashMap = new HashMap<Class<?>, ArrayList<Enum>>();

    /**
     * 获取类型的名称集合
     * 注意派生类实现的规范
     */
    public static final String[] getNames(Class type) {
        if (type == null) {
            return new String[0];
        }

        //检查是否派生至EnumBase
        if (!Enum.isValidEnumClass(type)) {
            return new String[0];
        }

        ArrayList names = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, null, null);
        String[] nameArr = new String[names.size()];
        names.toArray(nameArr);

        return nameArr;
    }

    /**
     * 获取枚举类型的值集合
     * @param type Class
     * @return int[]
     */
    public static int[] getValues(Class type) {
        if (type == null) {
            return new int[0];
        }

        if (!Enum.isValidEnumClass(type)) {
            return new int[0];
        }

        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, values, null);

        int[] valueArr = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            valueArr[i] = Integer.parseInt(values.get(i).toString());
        }
        return valueArr;

    }

    /**
     * 获取所有的枚举对象
     * @param type Class
     * @return Enum[]
     */
    public static Enum[] getEnums(Class type) {
        if (type == null) {
            return new Enum[0];
        }

        if (!Enum.isValidEnumClass(type)) {
            return new Enum[0];
        }

        ArrayList entries = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, null, entries);

        Enum[] enums = new Enum[entries.size()];
        entries.toArray(enums);
        return enums;
    }

    /**
     * 根据值获取枚举名称
     * @param type Class
     * @param value int
     * @return String
     */
    public static String getNameByValue(Class type, int value) {
        ArrayList names = new ArrayList();
        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, values, null);

        Integer valueObject =Integer.valueOf(value);
        if (!values.contains(valueObject)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = values.indexOf(valueObject);

        return (String) names.get(index);
    }

    /**
     * 根据枚举名称获取值
     * @param type Class
     * @param key String
     * @return int
     */
    public static int getValueByName(Class type, String name) {
        ArrayList names = new ArrayList();
        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, values, null);

        if (!names.contains(name)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = names.indexOf(name);
        return Integer.parseInt(values.get(index).toString());

    }

    /**
     * 根据枚举值返回所对应的枚举
     * @param type Class
     * @param value int
     * @return Enum
     */
    public static Enum parse(Class type, int value) {
        ArrayList values = new ArrayList();
        ArrayList entries = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, values, entries);

        Integer valueObject = Integer.valueOf(value);
        if (!values.contains(valueObject)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = values.indexOf(valueObject);
        return (Enum) entries.get(index);

    }

    /**
     * 根据名称解析枚举
     * @param type Class
     * @param name String
     * @return Enum
     */
    public static Enum parse(Class type, String name) {
        if (type == null || name == null) {
            return null;
        }

        ArrayList names = new ArrayList();
        ArrayList entries = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, null, entries);

        if (!names.contains(name)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = names.indexOf(name);
        return (Enum) entries.get(index);

    }

    /**
     * 是否定义了指定值的枚举字段
     * @param type Class
     * @param value int
     * @return boolean
     */
    public static boolean isDefined(Class type, int value) {
        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, values, null);
        Integer valueObject = Integer.valueOf(value);
        return values.contains(valueObject);
    }

    /**
     * 枚举类型中是否定义了指定名称的枚举字段
     * @param type Class
     * @param name String
     * @return boolean
     */
    public static boolean isDefined(Class type, String name) {
        ArrayList names = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, null, null);
        return names.contains(name);
    }

    /**
     * 构造函数
     * 派生类必须调用该函数以初始化枚举字段
     * @param value int
     * @param ugcValue int
     */
    protected Enum(int value, int ugcValue) {
        this.m_value = value;
        this.m_ugcValue = ugcValue;
    }


    /**
    * 获取枚举对应的UGC值
    * @param e Enum
    * @return int
    */
   protected static final int internalGetUGCValue(Enum e) {
       return e.getUGCValue();
   }

   /**
    * 根据UGC值获取对应的枚举
    * 供其它包派生
    * @param type Class
    * @param ugcValue int
    * @return Enum
    */
   protected static final Enum internalParseUGCValue(Class type, int ugcValue) {
       return Enum.parseUGCValue(type, ugcValue);
   }

   /**
    * 根据UGCValue获取枚举值
    * 内部使用，不检查类型
    * @param type Class
    * @param ugcValue int
    * @return Enum
    */
   static final Enum parseUGCValue(Class type, int ugcValue) {
       ArrayList entries = new ArrayList();
       ArrayList values = new ArrayList();
       Enum.getEnumNameValueAndEntry(type, null, values, entries);

       Integer value = Integer.valueOf(ugcValue);
       if (!values.contains(value)) {
           String message = InternalResource.loadString("ugcValue:"+ugcValue, InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
           throw new RuntimeException(message);
       }
       int index = values.indexOf(value);
       return (Enum) entries.get(index);
   }

    /**
     * 判断是否为有效的枚举字段
     * 有效的枚举，必须符合类型，且为public static final的
     * @param field Field
     * @param type Class
     * @return boolean
     */
    private static final boolean isValidEnumField(Field field) {
        if (field == null) {
            return false;
        }

        //枚举检查枚举的类型是否与枚举所在的类的类型相同
        Class type = field.getDeclaringClass();
        if (!field.getType().equals(type)) {
            return false;
        }

        //不合法的访问修饰符
        int moidifiers = field.getModifiers();
        if (!Modifier.isPublic(moidifiers) || !Modifier.isStatic(moidifiers) || !Modifier.isFinal(moidifiers)) {
            return false;
        }

        return true;
    }

    /**
     * 获取枚举类型中的名称、值及对象列表
     * 这些列表的顺序是一一对应的，这样方便相互查询
     * 如果不需获取某项的值，传入null即可
     * 为了提高效率，这里不对类型检查，由函数调用方保证
     * @param type Class
     * @param names ArrayList
     * @param values ArrayList
     * @param entries ArrayList
     */
    private static void getEnumNameValueAndEntry(Class type, ArrayList names, ArrayList values, ArrayList entries) {
        if (type == null) {
            return;
        }

        if (names == null && values == null && entries == null) {
            return;
        }

        //获取类型中的字段
        Field[] fields = type.getFields();
        if (fields == null || fields.length == 0) {
            return;
        }

        //遍历所有静态字段，查找符合条件的枚举值
        int len = fields.length;
        for (int i = 0; i < len; i++) {
            Field field = fields[i];

            //检查是否为有效的枚举字段
            if (!Enum.isValidEnumField(field)) {
                continue;
            }

            //获取该字段对应的枚举
            Enum e = null;
            try {
               
                e = (Enum) field.get(null);
            } catch (IllegalAccessException ex) {
                continue;
            } catch (IllegalArgumentException ex) {
                continue;
            }
            if (e != null) {
                if (names != null) {
                    names.add(field.getName());
                }
                if (values != null) {
                    Integer value = Integer.valueOf(e.value());
                    values.add(value);
                }
                if (entries != null) {
                    entries.add(e);
                }
            }
        }
        
        // 遍历m_hashMap中的值
        if (m_isCustom) {
        	for (Iterator<Map.Entry<Class<?>, ArrayList<Enum>>> i = m_hashMap
        			.entrySet().iterator(); i.hasNext();) {
        		Map.Entry<Class<?>, ArrayList<Enum>> e = i.next();
        		Class<?> tempClass = e.getKey();
        		if (tempClass.getName().equals(type.getName())) {
        			ArrayList<Enum> customEnums = e.getValue();
        			for (int j = 0; j < customEnums.size(); j++) {
        				Enum customEnum = customEnums.get(j);
        				if (names != null) {
							names.add(String.valueOf(customEnum.value()));
						}
        				if (values != null) {
        					values.add(customEnum.value());
						}
        				if (entries != null) {
        					entries.add(customEnum);
						}
        			}
        			break;
        		}
        	}
		}
    }

    /**
     * 是否为有效的枚举类
     * @param type Class
     * @return boolean
     */
    private static boolean isValidEnumClass(Class type) {
        if (type == null) {
            return false;
        }
        if (!(type.getSuperclass().equals(Enum.class))) {
            return false;
        }
        return true;
    }

    /**
     * 获取枚举对象的名称
     * @return String
     */
    public final String name() {
        return Enum.getNameByValue(this.getClass(), value());
    }

    /**
     * 获取枚举的值
     * @return int
     */
    public final int value() {
        return this.m_value;
    }

    /**
     * 枚举的字符串
     * 输出枚举名
     * @return String
     */
    public String toString() {
        return String.valueOf(name());
    }

    /**
     * 由于枚举值表现为静态的字段，因此直接比较引用便可知两枚举是否相等
     * @param other Object
     * @return boolean
     */
    public final boolean equals(Object other) {
        if(other == null){
            return false;
        }

        if(!this.getClass().equals(other.getClass())){
            return false;
        }

        com.supermap.data.Enum eOther = (com.supermap.data.Enum)other;
        if(this.value()!= eOther.value()){
            return false;
        }

        return true;
    }

    /**
     * 重写了equals方法，对应的应该重写hashCode
     * @return int
     */
    public final int hashCode() {
        return System.identityHashCode(this);
    }

    /**
     * 获取该枚举的UGC值
     * 供内部使用
     * 目前UGC值与UGO值统一了，但还是保留现有的实现方式
     * @return int
     */
    final int getUGCValue() {
        return this.m_ugcValue;
    }
    
    protected void setCustom(boolean value) {
		m_isCustom = value;
	}
}
