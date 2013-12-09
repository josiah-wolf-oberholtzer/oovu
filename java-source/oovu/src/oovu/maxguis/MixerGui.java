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
    private final int gutter = 10;
    private final int step = 145;

    public MaxPatcher build(RootServer root_server) {
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
            MaxPatcher patcher = new MaxPatcher(0, 0, 100, 100);
            int x_offset = 5;
            x_offset = this.fill_globals_section(patcher, x_offset);
            x_offset =
                this.fill_inputs_section(patcher, input_modules, x_offset);
            x_offset =
                this.fill_effects_section(patcher, effects_modules, x_offset);
            x_offset =
                this.fill_outputs_section(patcher, output_modules, x_offset);
            patcher.setBackgroundColor(0, 0, 0);
            patcher.send("statusbarvisible", Atom.parse("0"));
            patcher.send("toolbarvisible", Atom.parse("0"));
            patcher.getWindow().setTitle("Mixer");
            return patcher;
        } else {
            return null;
        }
    }

    private int fill_effects_section(
        MaxPatcher patcher,
        List<ModuleServer> modules,
        int x_offset) {
        return 0;
    }

    private int fill_globals_section(MaxPatcher patcher, int x_offset) {
        return 0;
    }

    private int fill_inputs_section(
        MaxPatcher patcher,
        List<ModuleServer> modules,
        int x_offset) {
        return 0;
    }

    public void fill_mixer_patcher(
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
            patcher
                .newDefault(
                    x_offset,
                    y_offset,
                    "bpatcher",
                    Atom.parse("@patching_rect " + x_offset + " "
                        + (y_offset + 115)
                        + " 140 595 @name oovu.mixer.sends @args "
                        + module.module_id));
        }
    }

    private int fill_outputs_section(
        MaxPatcher patcher,
        List<ModuleServer> modules,
        int x_offset) {
        return 0;
    }

    private MaxBox make_vertical_line(
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
