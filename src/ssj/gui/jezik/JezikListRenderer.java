package ssj.gui.jezik;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ssj.model.Jezik;

public class JezikListRenderer extends JLabel implements ListCellRenderer<Jezik> {
	private static final long serialVersionUID = -2166635978440396202L;
	
	public JezikListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Jezik> list, Jezik value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value.getNaziv());
		
		if (isSelected) {
		    setBackground(list.getSelectionBackground());
		    setForeground(list.getSelectionForeground());
		} else {
		    setBackground(list.getBackground());
		    setForeground(list.getForeground());
		}
		return this;
	}

}
