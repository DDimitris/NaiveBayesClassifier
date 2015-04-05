/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Objects;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class PrecisionRecallPair<K, V> {

    private K precision;
    private V recall;

    public PrecisionRecallPair() {
    }

    public K getPrecision() {
        return precision;
    }

    public void setPrecision(K precision) {
        this.precision = precision;
    }

    public V getRecall() {
        return recall;
    }

    public void setRecall(V recall) {
        this.recall = recall;
    }

    public void add(K key, V value) {
        this.precision = key;
        this.recall = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.precision);
        hash = 67 * hash + Objects.hashCode(this.recall);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrecisionRecallPair<K, V> other = (PrecisionRecallPair<K, V>) obj;
        if (!Objects.equals(this.precision, other.precision)) {
            return false;
        }
        if (!Objects.equals(this.recall, other.recall)) {
            return false;
        }
        return true;
    }
}
