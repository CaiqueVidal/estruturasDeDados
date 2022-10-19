package data.structures;

public class PilhaImpl<T> implements Pilha<T>{

    private final T[] elements;
    private int pos;

    public PilhaImpl(int size) {
        if (size <= 0) throw new RuntimeException("Tamanho inválido");
        this.elements = (T[]) new Object[size];
        this.pos = -1;
    }

    @Override
    public void push(T data) {
        if (isFull()) throw new RuntimeException("Pilha cheia");
        elements[++pos] = data;
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new RuntimeException("Pilha vazia");
        return elements[pos--];
    }

    @Override
    public T top() {
        return isEmpty() ? null : elements[pos];
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean isEmpty() {
        return pos < 0;
    }

    @Override
    public boolean isFull() {
        return (pos + 1 == size());
    }
}
