package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {	
	
	private List<Esame> allEsami;
	private Set<Esame> migliore;
	private double mediaMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.allEsami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		migliore = new HashSet<Esame>();
		mediaMigliore = 0.0;
		
		Set<Esame> parziale = new HashSet<Esame>();
		
		cercaMeglio(parziale,0,numeroCrediti);
		
		return migliore;
	}
	
	
	
	private void cercaMeglio(Set<Esame> parziale, int L, int numeroCrediti) {
		int sommaCrediti = sommaCrediti(parziale);
		
		// condizioni di uscita
		if(sommaCrediti > numeroCrediti)
			return;
		
		if(sommaCrediti == numeroCrediti) {
			double mediaVoti = calcolaMedia(parziale);
			if(mediaVoti > mediaMigliore) {
				mediaMigliore = mediaVoti;
				migliore = new HashSet<>(parziale);
			}
			return;
		}
		
		if(L == allEsami.size())
			return;
		
		// provo ad aggiungere il prossimo elemento
		parziale.add(allEsami.get(L));
		cercaMeglio(parziale,L+1,numeroCrediti);
		parziale.remove(allEsami.get(L));
		
		// provo a non aggiungere il prossimo elemento
		cercaMeglio(parziale,L+1,numeroCrediti);
	}
	

/*
	private void cerca(Set<Esame> parziale, int L, int numeroCrediti) {
		
		// calcolo il fitness della soluzione
		int sommaCrediti = sommaCrediti(parziale);
		
		// condizioni di uscita
		if(sommaCrediti > numeroCrediti)
			return;
		
		if(sommaCrediti == numeroCrediti) {
			double mediaVoti = calcolaMedia(parziale);
			if(mediaVoti > mediaMigliore) {
				mediaMigliore = mediaVoti;
				migliore = new HashSet<>(parziale);
			}
			return;
		}
		
		// se ho già aggiunto tutti gli esami
		if(L == allEsami.size())
			return;
		
		// se sono arrivato fin qui il numero di crediti > sommaCrediti quindi ha senso aggiungere esami
		
		for(Esame e: allEsami) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale,L+1,numeroCrediti);
				parziale.remove(e); //backtracking
			}
		}
		
		
	}*/

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
