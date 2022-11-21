package tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

//import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * @author francoise.perrin - 
 * Inspiration : http://www.jmdoudoux.fr/java/dej/chap-introspection.htm
 * 
 */
public class Introspection {

	private Introspection() {
	}

	/**
	 * Invocation d'une methode connaissant son nom sur un objet o
	 * en lui passant les bons parametres
	 * Le type des paramètres du constructeur sont obtenus dans la fonction
	 * Attention, ne fonctionne pas si ces types sont des interfaces et non des classes
	 * 
	 * @param o - l'objet sur lequel agit la methode
	 * @param params - la liste des parametres de la methode
	 * @param methodName - le nom de la methode
	 * @return la methode invoquee
	 * @throws Exception
	 */
	public static Object invoke(Object o, String methodName, Object[] params) throws Exception	{
		Class<? extends Object>[] paramTypes = null;
		if(params != null){
			paramTypes = new Class<?>[params.length];
			for(int i=0;i<params.length;++i)	{
				paramTypes[i] = params[i].getClass();
			}
		}
		Method m = o.getClass().getMethod(methodName,paramTypes);
		return m.invoke(o, params);
	}
	
	/**
	 * Invocation d'une methode connaissant son nom sur un objet o
	 * en lui passant les bons parametres
	 * le tableau de paramTypes contient les types des paramètres : utile quand ce sont des interfaces
	 * 
	 * @param o - l'objet sur lequel agit la methode
	 * @param params - la liste des parametres de la methode
	 * @param methodName - le nom de la methode
	 * @return la methode invoquee
	 * @throws Exception
	 */
	public static Object invoke(Object o, String methodName, Class<?> [] paramTypes, Object[] params) throws Exception	{
		
		Method m = o.getClass().getMethod(methodName,paramTypes);
		return m.invoke(o, params);
	}

	/**
	 * @param className
	 * @param methodName
	 * @param paramType
	 * @param param
	 * @return objet crée suite invocation méthode statique avec paramètres qui peuvent être des interfaces
	 */
	public static Object invoke (String className, String methodName, Class<?> [] paramTypes, Object[] params) {

		Object o = null;
		try {
			Class<?> c = Class.forName(className);
			Method m = c.getMethod(methodName,paramTypes);
			o =  m.invoke(null, params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	/**
	 * @param className
	 * @param methodName
	 * @param paramType
	 * @param param
	 * @return objet crée suite invocation méthode statique avec paramètres qui ne peuvent pas être des interfaces
	 */
	public static Object invoke (String className, String methodName, Object[] params) {

		Object o = null;
		try {
			Class<?> c = Class.forName(className);
			Class<? extends Object>[] paramTypes = null;
			if(params != null){
				paramTypes = new Class<?>[params.length];
				for(int i=0;i<params.length;++i)	{
					paramTypes[i] = params[i].getClass();
				}
			}
			Method m = c.getMethod(methodName,paramTypes);
			o =  m.invoke(null, params);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * cr�ation d'un objet connaissant le nom de la classe
	 * utilise un constructeur sans param�tre
	 * 
	 * @param className
	 * @return le nouvel objet cr�e
	 */
	public static Object newInstance(String className) {
		Object o = null;
		try	    {
			o = Class.forName (className).newInstance ();
		}
		catch (ClassNotFoundException e)	    {
			// La classe n'existe pas
			e.printStackTrace();
		}
		catch (InstantiationException e)	    {
			// La classe est abstract ou est une interface ou n'a pas de constructeur accessible sans param�tre
			e.printStackTrace();
		}
		catch (IllegalAccessException e)	    {
			// La classe n'est pas accessible
			e.printStackTrace();
		}
		return o;
	}



	
	/**
	 * Construction d'un objet a partir du nom de la classe et des parametres du constructeur
	 * le type des paramètres du constructeur sont obtenus dans la fonction
	 * Attention, ne fonctionne pas si ces types sont des interfaces et non des classes
	 * 
	 * @param className : le nom de la classe a instancier
	 * @param params : la liste des arguments du constructeur
	 * @return le nouvel objet 
	 */
	public static Object newInstance(String className, final Object... params) {
		Object o = null;
		try {
			Class<?> c = Class.forName(className);
			Class<? extends Object>[] paramTypes = null;

			if(params != null){
				paramTypes = new Class<?>[params.length];
				for(int i=0;i<params.length;++i)	{
					paramTypes[i] = params[i].getClass();
				}
			}
			Constructor<?> constructor = c.getConstructor(paramTypes);
			o = constructor.newInstance(params);
		} catch (Exception ex) {
			try {
				throw new InstantiationException(
						"Type '" + className +
						"' with arguments " + Arrays.asList(params) +
						" could not be instantiated: " + ex.getMessage());
			} catch (InstantiationException e) {
				// La classe est abstract ou est une interface 
				// ou n'a pas de constructeur accessible avec ces paramètres
				e.printStackTrace();
			}
		}
		return o;
	}

	/**
	 * Construction d'un objet a partir du nom de la classe et des parametres du constructeur
	 * le tableau de paramTypes contient les types des paramètres : utile quand ce sont des interfaces
	 * 
	 * @param className
	 * @param paramTypes
	 * @param params
	 * @return le nouvel objet 
	 */
	public static Object newInstance(String className, final Class<?> [] paramTypes, final Object[] params) {
		Object o = null;
		try {
			Class<?> c = Class.forName(className);
			Constructor<?> constructor = c.getConstructor(paramTypes);
			o = constructor.newInstance(params);
		} catch (Exception ex) {
			try {
				throw new InstantiationException(
						"Type '" + className +
						"' with arguments " + Arrays.asList(params) +
						" could not be instantiated: " + ex.getMessage());
			} catch (InstantiationException e) {
				// La classe est abstract ou est une interface 
				// ou n'a pas de constructeur accessible avec ces paramètres
				e.printStackTrace();
			}
		}
		return o;
	}
	public static void main(String[] args) {
		String p1 = new String("too");
		System.out.println(p1);
		String p2 = (String) newInstance( "java.lang.String", new Object[] {"ii"});
		System.out.println(p2);
		TestConstruct t1 = new TestConstruct(new TestClass());
		System.out.println(t1);
		TestConstruct t2 = (TestConstruct) newInstance( "tools.TestConstruct", new Class[] { TestInterface.class}, new Object[] {new TestClass()});
		System.out.println(t2);
	}
}


interface TestInterface{
	public void testFunction(int x);
}
class TestClass implements TestInterface{

	@Override
	public void testFunction(int x) {
		// TODO Auto-generated method stub
	}
}
class TestConstruct {
	public TestConstruct (TestInterface test){
		System.out.println("testConstruct");
	}
}
