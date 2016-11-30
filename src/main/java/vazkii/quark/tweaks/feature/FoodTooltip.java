package vazkii.quark.tweaks.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.quark.base.module.Feature;

public class FoodTooltip extends Feature {

	@SubscribeEvent
	public void makeTooltip(ItemTooltipEvent event) {
		if(event.getItemStack() != null && event.getItemStack().getItem() instanceof ItemFood) {
			int pips = ((ItemFood) event.getItemStack().getItem()).getHealAmount(event.getItemStack());
			int len = (int) Math.ceil((double) pips / 2);
			
			String s = " ";
			for(int i = 0; i < len; i++)
				s += "  ";
			
			event.getToolTip().add(1, s);
		}
	}

	@SubscribeEvent
	public void renderTooltip(RenderTooltipEvent.PostText event) {
		if(event.getStack() != null && event.getStack().getItem() instanceof ItemFood) {
			GlStateManager.pushMatrix();
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().bindTexture(GuiIngameForge.ICONS);
			int pips = ((ItemFood) event.getStack().getItem()).getHealAmount(event.getStack());
			for(int i = 0; i < Math.ceil((double) pips / 2); i++) {
				Gui.drawModalRectWithCustomSizedTexture(event.getX() + i * 9 - 2, event.getY() + 12, 16, 27, 9, 9, 256, 256);
				
				int u = 52;
				if(pips % 2 != 0 && i == 0)
					u += 9;
				
				Gui.drawModalRectWithCustomSizedTexture(event.getX() + i * 9 - 2, event.getY() + 12, u, 27, 9, 9, 256, 256);
			}

			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean hasSubscriptions() {
		return isClient();
	}

}