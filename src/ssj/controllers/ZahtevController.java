package ssj.controllers;

import java.util.ArrayList;
import java.util.List;

import ssj.gui.zahtev.prikaz.KreiraniShow;
import ssj.gui.zahtev.prikaz.ObradjeniShow;
import ssj.gui.zahtev.prikaz.ZahteviShow;
import ssj.managers.ZahtevManager;
import ssj.model.osoba.Sekretar;
import ssj.model.zahtev.Kreiran;
import ssj.model.zahtev.Odbijen;
import ssj.model.zahtev.Prihvacen;
import ssj.model.zahtev.UObradi;
import ssj.model.zahtev.Zahtev;

public class ZahtevController {

	public static void prikazKreiranih(Sekretar s) {
		List<Zahtev> kreirani = dobaviKreirane();
		new KreiraniShow(kreirani, s);
	}
	
	public static void prikazZahtevaUObradi(Sekretar s) {
		List<Zahtev> obradjeni = dobaviUObradi(s);
		new ObradjeniShow(obradjeni);
	}
	
	public static List<Zahtev> dobaviKreirane(){
		List<Zahtev> kreirani = new ArrayList<Zahtev>();

		for(Zahtev z:ZahtevManager.getInstance().getZahtevi()) {
			if(z.getStanje() instanceof Kreiran)
			{
				kreirani.add(z);
			}
		}
		
		return kreirani;
	}
	public static List<Zahtev> dobaviUObradi(Sekretar s) {
		List<Zahtev> obradjeni = new ArrayList<Zahtev>();

		for(Zahtev z: ZahtevManager.getInstance().getZahtevi()) {
			if(z.getSekretar() == null)
				continue;
			if(z.getStanje() instanceof UObradi && z.getSekretar().equals(s))
			{
				obradjeni.add(z);
			}
		}
		
		return obradjeni;
	}

	public static void prikazObradjenih(Sekretar s) {
		List<Zahtev> obradjeni = dobaviObradjene(s);
		new ZahteviShow(obradjeni);
	}

	private static List<Zahtev> dobaviObradjene(Sekretar s) {
		List<Zahtev> obradjeni = new ArrayList<Zahtev>();
		for(Zahtev z: ZahtevManager.getInstance().getZahtevi()) {
			if(z.getSekretar() == null)
				continue;
			if((z.getStanje() instanceof Prihvacen || z.getStanje() instanceof Odbijen) && z.getSekretar().equals(s))
			{
				obradjeni.add(z);
			}
		}
		
		return obradjeni;
	}

	public static void prikazObradjenih() {
		List<Zahtev> obradjeni = dobaviObradjene();
		new ZahteviShow(obradjeni);
		
	}

	private static List<Zahtev> dobaviObradjene() {
		List<Zahtev> obradjeni = new ArrayList<Zahtev>();
		for(Zahtev z: ZahtevManager.getInstance().getZahtevi()) {
			if(z.getSekretar() == null)
				continue;
			if(z.getStanje() instanceof Prihvacen || z.getStanje() instanceof Odbijen)
			{
				obradjeni.add(z);
			}
		}
		
		return obradjeni;
	}


}
