package tools.data;

import java.io.Serializable;


/**
 * @author francoise.perrin
 * 
 *Message envoyé à la view par le controler après action sur model
 */
public class TraceMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GUICoord coordInit, coordFinal;
	private ActionType actionType;
	private Couleur couleur;
	

	/**
	 * @param couleur 
	 * @param coordInit
	 * @param coordFinal
	 * @param actionType
	 */
	public TraceMessage(Couleur currentGamer, GUICoord pieceToMoveCoord, GUICoord targetCoord, ActionType actionType) {
		this.coordInit = pieceToMoveCoord;
		this.coordFinal = targetCoord;
		this.actionType = actionType;
		this.couleur = currentGamer;
	}

	public Couleur getCouleur() {
		return couleur;
	}
	
	/**
	 * @return the coordInit
	 */
	public GUICoord getCoordInit() {
		return coordInit;
	}
	
	/**
	 * @return the coordFinal
	 */
	public GUICoord getCoordFinal() {
		return coordFinal;
	}
	
	/**
	 * @return the actionType
	 */
	public ActionType getActionType() {
		return actionType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String st = couleur + " - " + coordInit + " -> " + coordFinal + " : " + actionType;
		return st;
				
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionType == null) ? 0 : actionType.hashCode());
		result = prime * result
				+ ((coordFinal == null) ? 0 : coordFinal.hashCode());
		result = prime * result
				+ ((coordInit == null) ? 0 : coordInit.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraceMessage other = (TraceMessage) obj;
		if (actionType != other.actionType)
			return false;
		if (coordFinal == null) {
			if (other.coordFinal != null)
				return false;
		} else if (!coordFinal.equals(other.coordFinal))
			return false;
		if (coordInit == null) {
			if (other.coordInit != null)
				return false;
		} else if (!coordInit.equals(other.coordInit))
			return false;
		return true;
	}
	
	
}
