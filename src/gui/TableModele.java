package gui;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

/**
 * @author Nathaniel Larouche
 *Classe qui sert de model a un JTable.
 *Il prend en parametre les données a fournir au tableau et ainsi qui les headers.
 */
public class TableModele extends AbstractTableModel { 
	private Object donnees[][];
	private String headers[];

	public TableModele() {

	}
	public TableModele(Object donnees[][], String Headers[]) { 
		super();
		this.donnees = donnees; 
		this.headers = Headers; 
	}
	public TableModele(TableModele modele) {
		super();
		this.donnees = modele.donnees;
		this.headers = modele.headers; 
	}
	public void setTableModele(Object donnees[][], String Headers[]) {
		this.donnees = donnees; 
		this.headers = Headers; 
	}
	public String[] getHeaders() {
		return this.headers;
	}
	public Object[][] getDonnees(){
		return this.donnees;
	}
	public void setHeaders(String[] headers) {
		this.headers= headers;
	}
	public void setDonnees(Object[][] donnees){
		this.donnees= donnees;
	}
	public int getColumnCount() { 
		return donnees[0].length; 
	}
	public Object getValueAt(int parm1, int parm2) { 
		return donnees[parm1][parm2]; 
	}
	public int getRowCount() { 
		return donnees.length; 
	}
	public String getColumnName(int col){ 
		return headers[col]; 
	}
	@Override
	public String toString() {
		return "TableModele [donnees=" + Arrays.toString(donnees) + ", headers=" + Arrays.toString(headers) + "]";
	}


}