package game.model.util.adt.list;


/**
 * Secure element which contains the element and a string identifying the 
 * element. This element is used inside the list of ClosedActions in 
 * SecureList.
 * 
 * It contains the current element of a single closed action and its title.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 * @param <ClosedActionType>
 */
public class ClosedAction<ClosedActionType> {

	
	/**
	 * The element which is the current element inside closed action.
	 */
	private Element<ClosedActionType> elem_secure;

    
    /**
     * The unique id of the current closed action.
     */
    private int id_secureList = 0;

    
    /**
     * The unique id of the current closed action.
     */
    private static int maxID = 0;

    
	/**
	 * The name identifying the closed action for debugging purpose and
	 * for being able to print the current list action.
	 */
	private String name;

	
	/**
	 * Constructor: (initializes) the content with start values.
	 */
	public ClosedAction() {

        //set title of transaction
        this.name = "";
        this.elem_secure = null;

        //set unique id and change the max id.
        id_secureList = maxID++;
	}
	
	
	/**
	 * Constructor: (initializes) the content with start values passed by 
	 * calling methods.
	 * @param _name 
	 * 					the name of the transaction
	 * @param _content	
	 * 					the current element in new closed action..
	 */
	public ClosedAction(
			final String _name, final Element<ClosedActionType> _content) {

        //set title of transaction
        this.name = _name;
        this.elem_secure = _content;
	}
	
	
	/**
	 * @return the elem_secure
	 */
	public final Element<ClosedActionType> getElem_secure() {
		return elem_secure;
	}
	
	
	/**
	 * @param _elem_secure the elem_secure to set
	 */
	public final void setElem_secure(
			final Element<ClosedActionType> _elem_secure) {
		this.elem_secure = _elem_secure;
	}
	
	
	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	
	
	/**
	 * @param _name the name to set
	 */
	public final void setName(final String _name) {
		this.name = _name;
	}


	/**
	 * @return the id_secureList
	 */
	public final int getId_secureList() {
		return id_secureList;
	}


}
