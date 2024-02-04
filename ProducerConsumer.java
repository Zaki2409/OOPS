class Buffer {
    int ptr=0;
    int buffer[] = new int[10];

    synchronized void put(int value) {
        if(ptr==10) {
            try {
                wait();
            }
            catch(InterruptedException e) {
                System.out.println(e);
            }
            buffer[ptr] = value;
            ptr ++;
            notifyAll();
        }
    }

    synchronized int get() {
        if(ptr ==0) {
            try{
                wait();
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
           
        }
        ptr --;
        notifyAll();
        return buffer[ptr];
        
       
    }
}


class consumer extends Thread {
   Buffer b;
    consumer (Buffer b) {
        this.b=b;
    }

    public void run() {
            int value;
            for(int i = 0 ; i <100 ; i++) {
                value = b.get();
                System.out.println("consumer cosumed: " + value );
            }
    }
}

class producer extends Thread {
    Buffer b;
    producer(Buffer b) {
        this.b =b;
    }
    public void run() {
        for(int i = 0 ; i <100; i++) {
            int value = i*3;
            b.put(value);
            System.out.println("producer:" + value);
            try {
                sleep(10);
            }
            catch(InterruptedException e){
                System.out.println(e);
            }

        }
    }
}

public class ProducerConsumer {
public static void main(String[] args) {
    Buffer b = new Buffer();
    producer p =new producer(b);
    consumer c = new consumer(b);
   // p.setPriority(Thread.MAX_PRIORITY);
    c.setPriority(Thread.MAX_PRIORITY);
    c.start();
    p.start();
    
}    
}
