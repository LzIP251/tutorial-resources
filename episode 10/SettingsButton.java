
public class SettingsButton {

    private Minecraft mc = Minecraft.getMinecraft();

    public float x,y,w,h;
    public HudMod mod;
    public Setting set;
    public boolean isSliding = false;
    int red =0,green=0,blue=0;

    int alpha = 170;

    public SettingsButton(float y, float x, float h, float w, Setting set) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.set = set;
    }
    public void drawBool(int mouseX,int mouseY) {

        ScaledResolution sr = new ScaledResolution(mc);

        boolean hovered = (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h);

        if (hovered)
            alpha = 150;
        else
            alpha = 100;

        Gui.drawRect(x, y, x + w, y + h, new Color(red, green, blue, alpha).getRGB());
        if(set instanceof BooleanSetting){
            mc.fontRendererObj.drawString(set.name, x + 2, y + 2, Color.white.getRGB());
            if (!hovered) {
                RenderUtil.drawHollowRect(x, y, x + w, y + h, Color.white.getRGB());
            } else {
                RenderUtil.drawHollowRect(x, y, x + w, y + h, Color.cyan.getRGB());
            }
        }else if(set instanceof ModeSetting){
            mc.fontRendererObj.drawString(set.name + ((ModeSetting) set).getMode(), x + 2, y + 2, Color.white.getRGB());
            if (!hovered){
                RenderUtil.drawHollowRect(x, y, x + w, y + h, Color.white.getRGB());
            }else{
                RenderUtil.drawHollowRect(x, y, x + w, y + h, Color.cyan.getRGB());
            }
        }else if(set instanceof NumberSetting){



            NumberSetting ns = (NumberSetting) set;
            if(isSliding){
                float value = (float)(mouseX - (x)) / (float)((w)/ns.getMaximum());
                value = MathHelper.clamp_float(value, ns.getMinimum(), ns.getMaximum());
                float f = this.denormalizeValue(value);
                ns.setValue(value);
                value = this.normalizeValue(f);
            }

            if (!hovered){
                Gui.drawRect(x, y, ((x + 1f) + (ns.getValue()/ns.getMaximum()*w)), (y + h), new Color(0, 194, 213,255).getRGB());
                RenderUtil.drawHollowRect(x, y, x + w, y + h, Color.white.getRGB());
            }else{
                Gui.drawRect(x, y, ((x + 1f) + (ns.getValue()/ns.getMaximum()*w)), (y + h), new Color(0, 194, 213,255).getRGB());
                RenderUtil.drawHollowRect(x, y, x + w, y + h, Color.cyan.getRGB());
            }

            mc.fontRendererObj.drawString(set.name + " " + ns.getValue(), x + 2, y + 2, Color.white.getRGB());
        }
    }

    public void onClick(int mouseX,int mouseY,int mouseButton){
        boolean hovered = (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h);


        if(mouseButton == 0 && hovered){
            if(set instanceof NumberSetting){
                this.isSliding = true;
            }
        }

        if(mouseButton == 0 && hovered){
                if(set instanceof BooleanSetting){
                    BooleanSetting booleanSetting = (BooleanSetting) set;
                    booleanSetting.toggle();
            }
            if(set instanceof ModeSetting){
                ModeSetting modeSetting = (ModeSetting) set;
                modeSetting.cycle();
            }
        }
    }
    public void mouseReleased() {
        isSliding = false;
    }

    public float normalizeValue(float val)
    {NumberSetting ns = (NumberSetting) set;
        return MathHelper.clamp_float((this.snapToStepClamp(val) - ns.getMinimum()) / (ns.getMaximum() - ns.getMinimum()), 0.0F, 1.0F);
    }

    public float denormalizeValue(float p_148262_1_)
    {NumberSetting ns = (NumberSetting) set;
        return this.snapToStepClamp(ns.getMinimum() + (ns.getMaximum() - ns.getMinimum()) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
    }

    public float snapToStepClamp(float p_148268_1_)
    {NumberSetting ns = (NumberSetting) set;
        p_148268_1_ = this.snapToStep(p_148268_1_);
        return MathHelper.clamp_float(p_148268_1_, ns.getMinimum(), ns.getMaximum());
    }

    protected float snapToStep(float p_148264_1_)
    {NumberSetting ns = (NumberSetting) set;
        if (ns.getIncrement() > 0.0F)
        {
            p_148264_1_ = ns.getIncrement() * (float)Math.round(p_148264_1_ / ns.getIncrement());
        }

        return p_148264_1_;
    }

}