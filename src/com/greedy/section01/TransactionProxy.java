package com.greedy.section01;

import static com.greedy.common.Template.getSqlSession;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.greedy.common.CategoryAndMenuDTO;
import com.greedy.common.MenuAndCategoryDTO;
import com.greedy.common.MenuDTO;

public class TransactionProxy {
    private Set<File> fileSet = new HashSet<>();
    private Class<?> transactionClass = null;
    private Object transactionObject = null;

    public TransactionProxy() {
        findAnnotatedClasses();
    }

    private void findAnnotatedClasses() {
        File directory = new File(".");

        listFilesRecursively(directory);

        for (File file : fileSet) {
            if (!file.getName().endsWith(".class")) {
                continue;
            }

            String className = file.getPath().replace(".class", "").replace(".\\bin\\", "").replace("\\", ".");
            
            try {
                Class<?> c = Class.forName(className);
                Annotation[] annotations = c.getAnnotations();

                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().getName().equals(Transaction.class.getName())) {
                        transactionClass = c;
                        transactionObject = transactionClass.getDeclaredConstructor().newInstance();
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMapper(SqlSession sqlSession) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Method method = null;

        try {
            method = transactionClass.getDeclaredMethod(methodName, ElementTestMapper.class);
            method.invoke(transactionObject, sqlSession.getMapper(ElementTestMapper.class));
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void listFilesRecursively(File dir) {
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                listFilesRecursively(file); // 하위 디렉토리인 경우 재귀 호출
            } else if (file.getName().endsWith(".class")) {
                fileSet.add(file);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T invokeTransactionMethod(String methodName, Object... args) {
        SqlSession sqlSession = getSqlSession();
        Class<?>[] parameters = null;
        T result = null;

        setMapper(sqlSession);

        for (Method method : transactionClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                parameters = method.getParameterTypes();
                break;
            }
        }

        try {
            Method method = transactionClass.getDeclaredMethod(methodName, parameters);
            result = (T) method.invoke(transactionObject, args);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getTargetException();
            cause.printStackTrace();
        }

        if (Integer.class.isInstance(result)) {
            int intValue = (int) result;

            if (intValue == 1) {
                System.out.println("Complete");
                sqlSession.commit();
            } else {
                System.out.println("Failed");
                sqlSession.rollback();
            }
        }

        sqlSession.close();
        return result;
    }

    List<String> selectCacheTest() {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    List<MenuDTO> selectResultMapTest() {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    List<MenuDTO> selectResultMapConstructorTest() {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    List<MenuAndCategoryDTO> selectResultMapAssociationTest() {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    List<CategoryAndMenuDTO> selectResultMapCollectionTest() {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    List<MenuDTO> selectSqlTest() {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    int insertMenuTest(MenuDTO menu) {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName(), menu);
    }

    int insertCategoryAndMenuTest(MenuAndCategoryDTO menuAndCategory) {
        return invokeTransactionMethod(Thread.currentThread().getStackTrace()[1].getMethodName(), menuAndCategory);
    }
}
