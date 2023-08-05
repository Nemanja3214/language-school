package ssj.utils;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Osoba;

public class ComboBoxRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 8511351141203931060L;

	public Component getListCellRendererComponent(
                                   JList list,
                                   Object value,
                                   int index,
                                   boolean isSelected,
                                   boolean cellHasFocus) {
        if (value instanceof Test) {
            value = ((Test)value).getNaziv();
        }
        if (value instanceof Kurs) {
            value = ((Kurs)value).getNaziv();
        }
        if (value instanceof Jezik) {
            value = ((Jezik)value).getNaziv();
        }
        if (value instanceof Osoba) {
            value = ((Osoba) value).getIme() + " " + ((Osoba) value).getPrezime();
        }
        if (value instanceof Termin) {
            value = ((Termin)value).getVremeOdrzavanja().format(KorisneFunkcije.dtf1);
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}

