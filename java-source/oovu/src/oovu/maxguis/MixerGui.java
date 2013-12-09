package oovu.maxguis;

import java.util.ArrayList;
import java.util.List;

import oovu.servers.DspSettingsServer;
import oovu.servers.ModuleServer;
import oovu.servers.RootServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxPatcher;

public class MixerGui {
    private static final int height = 745;
    private static final int gutter = 5;

    static public MaxPatcher build(RootServer root_server) {
        ArrayList<ModuleServer> effects_modules = new ArrayList<ModuleServer>();
        ArrayList<ModuleServer> input_modules = new ArrayList<ModuleServer>();
        ArrayList<ModuleServer> output_modules = new ArrayList<ModuleServer>();
        for (ModuleServer module : root_server.get_child_module_servers()) {
            DspSettingsServer dsp_settings = module.get_dsp_settings_server();
            boolean has_receives = dsp_settings.module_has_dsp_receives();
            boolean has_sends = dsp_settings.module_has_dsp_sends();
            if ((!has_receives) && (!has_sends)) {
                continue;
            } else if (has_receives && has_sends) {
                effects_modules.add(module);
            } else if (has_receives) {
                input_modules.add(module);
            } else {
                output_modules.add(module);
            }
        }
        if ((0 < effects_modules.size()) || (0 < input_modules.size())
            || (0 < output_modules.size())) {
            MaxPatcher patcher = new MaxPatcher(10, 10, 670, MixerGui.height);
            int x_offset = MixerGui.gutter;
            x_offset = MixerGui.fill_globals_section(patcher, x_offset);
            x_offset =
                MixerGui.fill_module_section("INPUTS", patcher, output_modules,
                    x_offset);
            x_offset =
                MixerGui.fill_module_section("THROUGHPUTS", patcher,
                    effects_modules, x_offset);
            x_offset =
                MixerGui.fill_module_section("OUTPUTS", patcher, input_modules,
                    x_offset);
            patcher.setBackgroundColor(0, 0, 0);
            patcher.send("enablevscroll", Atom.parse("0"));
            patcher.send(
                "window",
                Atom.parse("size 50 50 " + (x_offset + 50) + " "
                    + (MixerGui.height + 50)));
            patcher.send("window", Atom.parse("exec"));
            patcher.send("statusbarvisible", Atom.parse("0"));
            patcher.send("toolbarvisible", Atom.parse("0"));
            patcher.getWindow().setTitle("Mixer");
            return patcher;
        } else {
            return null;
        }
    }

    private static int fill_globals_section(MaxPatcher patcher, int x_offset) {
        patcher.newDefault(
            x_offset,
            MixerGui.gutter,
            "bpatcher",
            Atom.parse("@patching_rect " + MixerGui.gutter + " "
                + MixerGui.gutter + " 50 70 @name oovu.mixer.globals"));
        return x_offset + 50 + MixerGui.gutter;
    }

    private static void fill_module_column(
        ModuleServer module,
        MaxPatcher patcher,
        int x_offset,
        int y_offset) {
        DspSettingsServer dsp_settings = module.get_dsp_settings_server();
        boolean has_receives = dsp_settings.module_has_dsp_receives();
        boolean has_sends = dsp_settings.module_has_dsp_sends();
        if (has_receives || has_sends) {
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " " + y_offset
                    + " 140 110 @name oovu.mixer.title.basic @args "
                    + module.module_id + " @clickthrough 1 @background 1"));
        }
        if (has_receives) {
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " " + y_offset
                    + " 140 110 @name oovu.mixer.title.inputs @args "
                    + module.module_id + " @clickthrough 1"));
        }
        if (has_sends) {
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " " + y_offset
                    + " 140 110 @name oovu.mixer.title.outputs @args "
                    + module.module_id + " @clickthrough 1"));
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " "
                    + (y_offset + 115)
                    + " 140 595 @name oovu.mixer.sends @args "
                    + module.module_id));
        }
    }

    private static int fill_module_section(
        String comment_text,
        MaxPatcher patcher,
        List<ModuleServer> modules,
        int x_offset) {
        if (0 == modules.size()) {
            return x_offset;
        }
        MixerGui.make_vertical_line(patcher, x_offset, 735);
        x_offset += MixerGui.gutter + 5;
        patcher.newDefault(
            x_offset,
            MixerGui.gutter,
            "comment",
            Atom.parse("@text " + comment_text
                + " @fontface 3 @textcolor 1 1 1 1 @patching_rect " + x_offset
                + " " + MixerGui.gutter + " 140 20"));
        for (ModuleServer module : modules) {
            MixerGui.fill_module_column(module, patcher, x_offset,
                MixerGui.gutter + 25);
            x_offset += 140 + MixerGui.gutter;
        }
        return x_offset;
    }

    private static MaxBox make_vertical_line(
        MaxPatcher patcher,
        int x_offset,
        int height) {
        return patcher.newDefault(
            x_offset,
            height,
            "live.line",
            Atom.parse("@patching_rect " + x_offset + " 5 5 " + height
                + " @border 2 @justification 1"));
    }
}
