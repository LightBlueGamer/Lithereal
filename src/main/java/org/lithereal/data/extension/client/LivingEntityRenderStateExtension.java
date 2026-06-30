package org.lithereal.data.extension.client;

public interface LivingEntityRenderStateExtension {
    default void lithereal$setChill(int chill) {
        throw new IllegalStateException("Implemented via mixin");
    }
    default int lithereal$getChill() {
        throw new IllegalStateException("Implemented via mixin");
    }
}
