package codigo;

import java.util.Objects;

class Par {
        int v, w;

        Par(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Par)) return false;
            Par pair = (Par) o;
            return v == pair.v && w == pair.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v, w);
        }
    }