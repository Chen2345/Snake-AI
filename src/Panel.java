import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class Panel extends JPanel implements ActionListener {
    int serpente = 5;
    int meleMangiate = 0;
    final static int campo = 500;
    final static int grandezzaCella = 50;
    final static int nCelle = campo * campo / grandezzaCella;
    int x[] = new int[nCelle];
    int y[] = new int[nCelle];
    int melaX;
    int melaY;
    boolean stato = false;
    int delay = 100;
    Timer timer;
    Random random;
    boolean A = true;
    boolean S = true;
    boolean D = true;
    boolean W = true;

    int giri=0;
    int k=0;
    boolean bandiera=true;
    boolean bandiera2=true;
    boolean bandiera3=true;
    public Panel() {
        random = new Random();
        this.setPreferredSize(new Dimension(campo, campo));
        this.setBackground(Color.GREEN);
        this.setFocusable(true);
        generaMela();
        stato = true;
        timer = new Timer(delay, this);
        timer.start();
    }

   public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        for (int i = 0; i < nCelle; i++) {
            g.drawLine(i * grandezzaCella, 0, i * grandezzaCella, campo);  //linee verticali
            g.drawLine(0, i * grandezzaCella, campo, i * grandezzaCella);  //linee orizzontali
        }
        g.setColor(Color.red);
        g.fillOval(melaX, melaY, grandezzaCella, grandezzaCella);
        ;
        for (int i = 0; i < serpente; i++) {
            if (i == 0) {
                g.setColor(Color.BLUE);
                g.fillRect(x[i], y[i], grandezzaCella, grandezzaCella);
            } else {
                g.setColor(Color.CYAN);
                g.fillRect(x[i], y[i], grandezzaCella, grandezzaCella);
            }
        }
    }

    public void generaMela() {
        do {
            melaX = random.nextInt(campo / grandezzaCella) * grandezzaCella;
            melaY = random.nextInt(campo / grandezzaCella) * grandezzaCella;
        } while (controlloGeneraMela());
    }

    private boolean controlloGeneraMela() {
        for (int i = 0; i < serpente; i++)
            if (melaY == y[i] && melaX == x[i]) return true;
        return false;
    }

    private boolean noDirezione(){
        if(W)
            return false;
        if(S)
            return false;
        if(A)
            return false;
        if(D)
            return false;
        stato=false;
        return true;
    }
   /* private boolean evitaCollisioneX(int direzione) {
        for (int i = serpente; i > 0; i--)
            if (x[0] + direzione == x[i] && y[0] == y[i]) {
                 return cambiadirezioneX();
            }
        if (x[0]+direzione < 0)
        {
            return cambiadirezioneX();
        }
        if (x[0]+direzione > campo)   {
            return cambiadirezioneX();
        }
        return true;
    }
    private boolean evitaCollisioneY(int direzione) {
        for (int i = serpente; i > 0; i--)
            if (x[0] == x[i] && y[0] + direzione == y[i]) {
                return cambiadirezioneY();
            }
        if (y[0]+direzione < 0)     {
            return cambiadirezioneY();
        }
        if (y[0]+direzione > campo)       {
            return cambiadirezioneY();
        }
        return true;
    }*/

    /*private void cambiadirezioneX(){
        if(y[0]>melaY){
            basso();
        }
        else alto();
    }
    private void cambiadirezioneY(){
        if(x[0]>melaX){
            sinistra();
        }
        else destra();
    }
    public void movimento() {
        if (melaX != x[0]) {
            if (melaX > x[0]) {
                if (!D) {
                    if (y[0] >= campo / 2) {
                        alto();
                    } else basso();
                } else {
                    destra();
                }
            } else {
                if (!A) {
                    if (y[0] >= campo / 2) {
                        alto();
                    } else basso();
                } else {
                    sinistra();
                }
            }
        } else if (melaY != y[0]) {
            if (melaY > y[0]) {
                if (!S) {
                    if (x[0] >= campo / 2) {
                        sinistra();
                    } else destra();
                } else {
                    basso();
                }
            } else {
                if (!W) {
                    if (x[0] >= campo / 2) {
                        sinistra();
                    } else destra();
                } else alto();
            }
        }
    } */
    private void movimento() throws InterruptedException {
        if(melaX>x[0]&&bandiera&&melaY>=y[0]){
            destra();
        }
        else if(melaY>y[0]&&bandiera|| y[0]<campo-grandezzaCella-giri*grandezzaCella){
            basso();
        }
        else bandiera=false;
        giri=serpente/(campo/grandezzaCella);
        if(k<giri&&y[0]==campo-giri*grandezzaCella-grandezzaCella&&bandiera3)
            if(x[0]<campo-grandezzaCella&&bandiera2)
            {
                destra();
            }
            else if(k==0)
            {
                basso();
                bandiera2=false;
            }
            else if(x[0]>=grandezzaCella)
                sinistra();
            else{
                k++;
                bandiera2=true;
            }
        else
        if(!bandiera){
            {
                bandiera3=false;
                if(x[0]>0&&!bandiera)
                    sinistra();
                else if(y[0]>0&&!bandiera)
                    alto();
                else{
                    k=0;
                    bandiera=true;
                    bandiera3=true;
                }
            }
        }
    }
    private void spostaCorpo() {
        for (int i = serpente; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
    }
    private boolean statoAttualeX(int direzione){
        for (int i = serpente; i > 0; i--)
            if (x[0] + direzione == x[i] && y[0] == y[i]) {
                return false;
            }
        return true;
    }
    private boolean statoAttualeY(int direzione){
        for (int i = serpente; i > 0; i--)
            if (x[0]== x[i] && y[0] + direzione == y[i]) {
                return false;
            }
        return true;
    }
    public void destra() {
        if(noDirezione())
            return;
            if (D){
                spostaCorpo();
                x[0] = x[0] + grandezzaCella;
                A = false;
                S = statoAttualeY(grandezzaCella);
                D = statoAttualeX(grandezzaCella);
                W = statoAttualeY(-grandezzaCella);
                if(noDirezione())
                    return;
            }
    }

    public void sinistra() {
        if(noDirezione())
            return;
            if (A)
            {
                spostaCorpo();
                x[0] = x[0] - grandezzaCella;
                A = statoAttualeX(-grandezzaCella);
                S = statoAttualeY(grandezzaCella);
                D = false;
                W = statoAttualeY(-grandezzaCella);
                if(noDirezione())
                    return;
            }
    }

    public void alto() {
        if(noDirezione())
            return;
            if (W) {
                spostaCorpo();
                y[0] = y[0] - grandezzaCella;
                A = statoAttualeX(-grandezzaCella);
                S = false;
                D = statoAttualeX(grandezzaCella);
                W = statoAttualeY(-grandezzaCella);
                if(noDirezione())
                    return;
            }
    }

    public void basso() {
        if(noDirezione())
            return;
            if (S){
                spostaCorpo();
                y[0] = y[0] + grandezzaCella;
                A = statoAttualeX(-grandezzaCella);
                S = statoAttualeY(grandezzaCella);
                D = statoAttualeX(grandezzaCella);
                W = false;
                if(noDirezione())
                    return;
            }
    }
    public void melaMangiato() {
        if (x[0] == melaX && y[0] == melaY) {
            serpente++;
            generaMela();
        }
    }

    private void collisione() {
        for (int i = serpente; i > 0; i--)
            if (x[0] == x[i] && y[0] == y[i]) stato = false;
        if (x[0] < 0) stato = false;
        if (x[0] > campo) stato = false;
        if (y[0] < 0) stato = false;
        if (y[0] > campo) stato = false;
        if (!stato) timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        if (stato) {
            try {
                movimento();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            melaMangiato();
            collisione();
        }
        else System.out.println("1");
        repaint();
    }
}

