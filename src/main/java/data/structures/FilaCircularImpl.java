package data.structures;

public class FilaCircularImpl<T> implements Fila<T>{

    private final T[] elements;
    private int front;
    private int rear;

    private int numberOfElements;

    public FilaCircularImpl(int size) {
        if (size <= 0) throw new RuntimeException("Tamanho inválido");
        this.elements = (T[]) new Object[size];
        this.front = -1;
        this.rear = -1;
        this.numberOfElements = 0;
    }

    @Override
    public void enqueue(T data) {
        if (isFull()) throw new RuntimeException("Fila cheia");

        if (isEmpty()) {
            front = numberOfElements;
            rear = numberOfElements;
            elements[numberOfElements] = data;
        } else if (rear+1 < size()) {
            elements[++rear] = data;
        } else {
            rear = 0;
            elements[rear] = data;
        }

        numberOfElements++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Fila vázia");

        numberOfElements--;

        if (front+1 < size()) {
            return elements[front++];
        } else {
            front = 0;
            return elements[size()-1];
        }
    }

    @Override
    public T front() {
        return isEmpty() ? null : elements[front];
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    @Override
    public boolean isFull() {
        return numberOfElements == size();
    }
}