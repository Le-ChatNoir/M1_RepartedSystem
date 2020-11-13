package bean;

import java.io.Serializable;

import annotation.Id;
import annotation.Table;

//Les annotations sont des commentaires qui seront lus par Java a l'execution, 
//comme une interface, un nom (Table) et ses attributs (name)
//Mais on doit construire Table
@SuppressWarnings("serial")
@Table(name="t_image")
public class PicServ implements Serializable{
	
	//Indication que l'ID est en auto-increment pour que la methode enregistrer() l'ignore
	@Id
	Integer idImage = null;
	
	String titre = null;
	int[] jpeg;

	public PicServ(String titre,
			int[] jpeg) {
		this.titre = titre;
		this.jpeg = jpeg;
	}

	public Integer getIdLivre() {
		return idImage;
	}
	public void setIdLivre(Integer idImage) {
		this.idImage = idImage;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public int[] getJpeg() {
		return jpeg;
	}
	public void setJpeg(int[] jpeg) {
		this.jpeg = jpeg;
	}
	
	
	
}
