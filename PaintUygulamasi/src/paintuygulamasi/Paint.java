package paintuygulamasi;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.geom.Line2D;

public class Paint extends JFrame{
	
	JButton fircaButton, cizgiButton, dikdortgenButton, elipsButton, kenarlikButton, dolguButton;
        
	int butonSirasi=1;
	
	Color kenarlikRengi= Color.BLACK, dolguRengi= Color.BLACK;	
	Graphics2D grafikAyarlari;
	public Paint() {
                this.setBackground(Color.YELLOW);
		this.setSize(1500,1000);
		this.setTitle("Paint Uygulamam");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel buttonPanel = new JPanel();
		Box kutu= Box.createHorizontalBox();
		
		fircaButton= butonYap("C:\\Users\\Elif\\eclipse-workspace\\PaintUygulamasi2\\src\\firca.png",1);
		cizgiButton= butonYap("C:\\Users\\Elif\\eclipse-workspace\\PaintUygulamasi2\\src\\cizgi.png",2);
		elipsButton= butonYap("C:\\Users\\Elif\\eclipse-workspace\\PaintUygulamasi2\\src\\elips.png",3);
		dikdortgenButton= butonYap("C:\\Users\\Elif\\eclipse-workspace\\PaintUygulamasi2\\src\\kare.png",4);
              
		kenarlikButton= renkliButonYap("C:\\Users\\Elif\\eclipse-workspace\\PaintUygulamasi2\\src\\dolgu.png",5,true);
		dolguButton= renkliButonYap("C:\\Users\\Elif\\eclipse-workspace\\PaintUygulamasi2\\src\\dolgu.png",6,false);
		
		kutu.add(fircaButton);
		kutu.add(cizgiButton);
		kutu.add(elipsButton);
		kutu.add(dikdortgenButton);
		kutu.add(kenarlikButton);
		kutu.add(dolguButton);
		
		buttonPanel.add(kutu);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(new CizimTahtasi(), BorderLayout.CENTER);
		this.setVisible(true);
		
	}	// constructor sonu
	
	public JButton butonYap(String iconDosyasi, final int hareketSayisi)
	{
		JButton buton=new JButton();
		Icon butIcon=new ImageIcon(iconDosyasi);
		buton.setIcon(butIcon); // butonumuza ikonumuzu ekledik.
		
		buton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				butonSirasi=hareketSayisi;
				
			}					
		});
		return buton;
	} // butonYap metodunun sonu
	public JButton renkliButonYap(String iconDosyasi, final int hareketSayisi,final boolean kenarlik) {
		JButton buton=new JButton();
		Icon butIcon=new ImageIcon(iconDosyasi);
		buton.setIcon(butIcon); 
                
		buton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(kenarlik) {
					kenarlikRengi= JColorChooser.showDialog(null, "KENARLIK RENGİ SEÇ", Color.BLACK);
				}else {
					dolguRengi= JColorChooser.showDialog(null, "DOLGU RENGİ SEÇ", Color.BLACK);
				}		
			}					
		});
		return buton;
	}// renkli buton sonu
	private class CizimTahtasi extends JComponent{
                
		ArrayList<Shape> sekiller = new ArrayList<Shape>();
		ArrayList<Color> dolguSekli= new ArrayList<Color>();
		ArrayList<Color> kenarlikSekli= new ArrayList<Color>();
		
		Point cizimBaslangic, cizimSon;
		
		public CizimTahtasi()
		{
                        	this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(butonSirasi != 1) {
					cizimBaslangic= new Point(e.getX(), e.getY());
					cizimSon= cizimBaslangic;
					repaint();	
					}
				}
				public void mouseReleased(MouseEvent e) {
					
					if(butonSirasi != 1) {
						Shape sekil = null;
                                        	
					if(butonSirasi == 2) {
						 sekil= cizgiCiz(cizimBaslangic.x,cizimBaslangic.y,e.getX(),e.getY());
					}else if(butonSirasi == 3) {
						 sekil= elipsCiz(cizimBaslangic.x,cizimBaslangic.y,e.getX(),e.getY());
					}else if(butonSirasi == 4) {
                                                 sekil= dikdortgenCiz(cizimBaslangic.x,cizimBaslangic.y,e.getX(),e.getY());
					}
					sekiller.add(sekil);
					dolguSekli.add(dolguRengi);
					kenarlikSekli.add(kenarlikRengi);
					
					cizimBaslangic=null;
					cizimSon=null;
					repaint();
					}
				}

                         
			});// addmouselistener sonu
			this.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
                                    if(butonSirasi==1){
					int x= e.getX();
                                        int y=e.getY();
                                        Shape sekil=null;
                                        kenarlikRengi=dolguRengi;
                                        sekil=fircaCiz(x,y,5,5);
                                        
                                        sekiller.add(sekil);
                                        dolguSekli.add(dolguRengi);
                                        kenarlikSekli.add(kenarlikRengi);
                                }
                                        cizimSon=new Point(e.getX(),e.getY());
					repaint();
				}
			}); //mousemotion sonu
		}
		public void paint(Graphics g) {
                        grafikAyarlari=(Graphics2D)g;
			grafikAyarlari.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			grafikAyarlari.setStroke(new BasicStroke(4));
			
			Iterator<Color> kenarlikSay= kenarlikSekli.iterator();
			Iterator<Color> dolguSay= dolguSekli.iterator();
			
			
			
			for(Shape s:sekiller) {
				grafikAyarlari.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				grafikAyarlari.setPaint(kenarlikSay.next());
				grafikAyarlari.draw(s);
				grafikAyarlari.setPaint(dolguSay.next());
				grafikAyarlari.fill(s);				
			}
			if(cizimBaslangic != null && cizimSon != null) {
				
				grafikAyarlari.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				grafikAyarlari.setPaint(Color.GRAY);
                                Shape sekil=null;
				if(butonSirasi==2){
				sekil= cizgiCiz(cizimBaslangic.x,cizimBaslangic.y, cizimSon.x,cizimSon.y);
                                }else if(butonSirasi==3){
                                sekil= elipsCiz(cizimBaslangic.x,cizimBaslangic.y, cizimSon.x,cizimSon.y);
                                }else if(butonSirasi==4){
                                sekil= dikdortgenCiz(cizimBaslangic.x,cizimBaslangic.y, cizimSon.x,cizimSon.y);
                                }
                                
                            grafikAyarlari.draw(sekil);
                            
                                
			}			
		}

       	}	
	private Rectangle2D.Float dikdortgenCiz(int x1, int y1, int x2, int y2){
		int x= Math.min(x1, x2);
		int y= Math.min(y1, y2);
		
		int genislik = Math.abs(x1-x2);
		int yukseklik= Math.abs(y1-y2);
		
		return new Rectangle2D.Float(x, y, genislik, yukseklik);
	}
        private Ellipse2D.Float elipsCiz(int x1, int y1, int x2, int y2){
		int x= Math.min(x1, x2);
		int y= Math.min(y1, y2);
		
		int genislik = Math.abs(x1-x2);
		int yukseklik= Math.abs(y1-y2);
		
		return new Ellipse2D.Float(x, y, genislik, yukseklik);
	}
        private Line2D.Float cizgiCiz(int x1, int y1, int x2, int y2){
		return new Line2D.Float(x1,y1,x2,y2);		
	}
        
        private Ellipse2D.Float fircaCiz(int x1,int y1, int fircaGenislik, int fircaYukseklik){
        return new Ellipse2D.Float(x1,y1,fircaGenislik,fircaYukseklik);
        }
        
        public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                Paint p = new Paint();
                p.setVisible(true);
            }
        });        
    }
	
}


