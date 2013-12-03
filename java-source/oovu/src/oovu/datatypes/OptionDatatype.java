package oovu.datatypes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class OptionDatatype extends StringDatatype {
    protected int option_index;
    protected List<String> options;

    public OptionDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public OptionDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            // NEXT
            MessageHandlerBuilder next_builder =
                new MessageHandlerBuilder("next");
            next_builder.with_arity(0);
            next_builder.with_callback(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    OptionDatatype.this.client.reoutput_value();
                    return null;
                }
            });
            next_builder.with_is_binding_relevant(true);
            next_builder.with_setter(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    OptionDatatype.this.next_option();
                    return null;
                }
            });
            this.client.add_message_handler(next_builder.build(this.client));
            // OPTIONS
            MessageHandlerBuilder options_builder =
                new MessageHandlerBuilder("options");
            options_builder.with_callback(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler message_handler,
                    Atom[] arguments) {
                    OptionDatatype.this.client.make_request(
                        OptionDatatype.this.client,
                        message_handler.get_getter_name(), null);
                    OptionDatatype.this.client.reoutput_value();
                    return null;
                }
            });
            options_builder.with_getter(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    return Atoms.to_atoms(built_message_handler.get_name(),
                        OptionDatatype.this.get_options()
                            .toArray(new String[0]));
                }
            });
            options_builder.with_is_meta_relevant(true);
            options_builder.with_is_state_relevant(true);
            options_builder.with_setter(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    String[] options =
                        OptionDatatype.this
                            .extract_strings_from_atoms(arguments);
                    OptionDatatype.this.set_options(options);
                    return null;
                }
            });
            this.client.add_message_handler(options_builder.build(this.client));
            // PREVIOUS
            MessageHandlerBuilder previous_builder =
                new MessageHandlerBuilder("previous");
            previous_builder.with_arity(0);
            previous_builder.with_callback(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    OptionDatatype.this.client.reoutput_value();
                    return null;
                }
            });
            previous_builder.with_is_binding_relevant(true);
            previous_builder.with_setter(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    OptionDatatype.this.previous_option();
                    return null;
                }
            });
            this.client
                .add_message_handler(previous_builder.build(this.client));
        }
    }

    public int get_option_index() {
        return this.option_index;
    }

    public List<String> get_options() {
        return this.options;
    }

    protected void initialize_options(Map<String, Atom[]> argument_map) {
        String[] options = new String[0];
        if (argument_map.containsKey("options")) {
            options =
                this.extract_strings_from_atoms(argument_map.get("options"));
        }
        this.set_options(options);
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
        this.initialize_options(argument_map);
    }

    public void next_option() {
        int index = this.option_index + 1;
        if (this.options.size() <= index) {
            index = 0;
        }
        this.set_value(Atom.newAtom(new int[] {
            index
        }));
    }

    public void previous_option() {
        int index = this.option_index - 1;
        if (index < 0) {
            index = this.options.size() - 1;
        }
        this.set_value(Atom.newAtom(new int[] {
            index
        }));
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Atom[] result = new Atom[1];
        if ((input.length == 1) && input[0].isInt() && (0 <= input[0].toInt())
            && (input[0].toInt() < this.options.size())) {
            int index = input[0].toInt();
            try {
                result = Atom.newAtom(new String[] {
                    this.options.get(index)
                });
                this.option_index = index;
            } catch (IndexOutOfBoundsException e) {
            }
        } else {
            String input_string = Atom.toOneString(input);
            if (this.options.contains(input_string)) {
                this.option_index = this.options.indexOf(input_string);
            } else {
                this.option_index = 0;
            }
        }
        result[0] = Atom.newAtom(this.options.get(this.option_index));
        return result;
    }

    public void set_options(String[] options) {
        if (0 == options.length) {
            options = new String[] {
                "---"
            };
        }
        this.options = Arrays.asList(options);
        if (this.value != null) {
            this.set_value(this.get_value());
        }
    }
}
