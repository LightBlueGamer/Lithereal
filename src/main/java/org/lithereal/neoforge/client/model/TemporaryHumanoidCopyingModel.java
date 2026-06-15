package org.lithereal.neoforge.client.model;

//? neoforge {
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.neoforged.neoforge.client.ClientHooks;

public final class TemporaryHumanoidCopyingModel<S extends HumanoidRenderState> extends HumanoidModel<S> {
	private final HumanoidModel<? super S> source;
	private final HumanoidModel<? super S> delegate;
	private final boolean setDelegateAngles;

	public static <S extends HumanoidRenderState> TemporaryHumanoidCopyingModel<S> create(HumanoidModel<? super S> source, HumanoidModel<? super S> delegate, boolean setDelegateAngles) {
		return new TemporaryHumanoidCopyingModel<>(source, delegate, setDelegateAngles);
	}

	private TemporaryHumanoidCopyingModel(HumanoidModel<? super S> source, HumanoidModel<? super S> delegate, boolean setDelegateAngles) {
		super(delegate.root(), delegate::renderType);
		this.source = source;
		this.delegate = delegate;
		this.setDelegateAngles = setDelegateAngles;
	}

	@Override
	public void setupAnim(S state) {
		resetPose();
		source.setupAnim(state);
		ClientHooks.copyModelProperties(source, delegate);

		if (setDelegateAngles) {
			delegate.setupAnim(state);
		}
	}
}
//?}