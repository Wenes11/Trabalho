import java.util.concurrent.Semaphore;

class Filosofo extends Thread {
    private int id;
    private Semaphore garfoEsquerda, garfoDireita;

    public Filosofo(int id, Semaphore garfoEsquerda, Semaphore garfoDireita) {
        this.id = id;
        this.garfoEsquerda = garfoEsquerda;
        this.garfoDireita = garfoDireita;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void pegarGarfos() throws InterruptedException {
        garfoEsquerda.acquire();
        garfoDireita.acquire();
        System.out.println("Filósofo " + id + " pegou os garfos.");
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comendo.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void devolverGarfos() {
        garfoEsquerda.release();
        garfoDireita.release();
        System.out.println("Filósofo " + id + " devolveu os garfos.");
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                pegarGarfos();
                comer();
                devolverGarfos();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class JantarFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Semaphore[] garfos = new Semaphore[numFilosofos];
        Filosofo[] filosofos = new Filosofo[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Semaphore(1);
        }

        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % numFilosofos]);
            filosofos[i].start();
        }
    }
}
