////////////////////////
/** GLOBAL VARIABLES **/
////////////////////////

autowatch = 1;
inlets = 1;
outlets = 3;
DEBUG = true;

var environment_dict = new Dict('OOVU');
if (!environment_dict.contains('initialized')) {
    var INNER_DICT = new Dict();
    environment_dict.set('initialized', true);
    environment_dict.replace('modules::names', INNER_DICT); 
    environment_dict.replace('modules::objects', INNER_DICT);
    environment_dict.replace('osc-addresses', INNER_DICT);
    environment_dict.replace('pull-addresses', INNER_DICT);
    environment_dict.replace('push-addresses', INNER_DICT);
    INNER_DICT.freepeer();
}

function loadbang() {
    //Environment.notify();
    outlet(2, 'bang');
}

function bang() {
    Environment.notify();
}

function notifydeleted() {
    //Environment.notify();
}

///////////////////
/** ENVIRONMENT **/
///////////////////

var Environment = {
        NULL: 'NULL',
        add_interface: function(object_id, object_dict, 
            interface_name, interface_attribute_name) {
            object_dict.set('interface::' + interface_attribute_name, 
                object_id + '-' + interface_name + '-interface');
        },
        debug: function(source, message, indent_level, warn) {
            if (indent_level === undefined) {
                indent_level = 0;
            }
            if (DEBUG) {
                message = this.indent(indent_level) + 
                    '[' + (Date.now() % 1000000) / 1000. + '] ' +
                    source + ': ' + 
                    message + 
                    '\n';
                if (warn) {
                    error(message);
                } else {
                    post(message);
                }
            }
        },
        debug_finished: function(message, start_time, stop_time) {
            post(
                message +
                ' Finished ' + 
                ((start_time % 1000000) / 1000.) + 
                ':' + 
                ((stop_time % 1000000) / 1000.) + 
                ' [' + (stop_time - start_time) + 'ms]' +
                '\n'
                );
        },
        find_unique_name: function(desired_name, namespace) {
            this.debug('Environment', 'find_unique_name()', 8);
            var currently_desired_name = desired_name;
            var counter = 1;
            if (namespace.contains(desired_name)) {
                currently_desired_name = desired_name + '.' + counter;
                while (namespace.contains(currently_desired_name)) {
                    counter += 1;
                    currently_desired_name = desired_name + '.' + counter;
                }
            }
            return currently_desired_name;
        },
        get_keys_safely: function(dict) {
            var keys = dict.getkeys(1);
            if (keys === null) {
                keys = [];
            } else if (typeof keys === 'string') {
                keys = [keys];
            } else if (! keys instanceof Array) {
                keys = [keys];
            }
            return keys;
        },
        get_modules: function() {
            var modules = [];
            
            var module_names = Environment.get_keys_safely(
                environment_dict.get('modules::names'));
            if (typeof module_names === 'string') {
                module_names = [module_names];
            }
            if (module_names !== null) {
                for (var i = 0, j = module_names.length; i < j; i++) {
                    var module_id = environment_dict.get(Environment.join([
                        'modules::names', module_names[i]]));
                    var module = new Module(module_id);
                    modules.push(module);
                }
            }
            return modules;
        },
        indent: function(n) {
            for (var indent = ''; indent.length < n;) {
                indent += ' ';
            }
            return indent;
        },
        is_hash_variable: function(x) {
            if ((typeof x === 'string') && (x.length) && (x.charAt(0) === '#')) {
                return true;
            }
            return false;
        },
        join: function(path_parts) {
            var result = path_parts[0];
            for (var i = 1, j = path_parts.length; i < j; i++) {
                result = result + '::' + path_parts[i];
            }
            return result;    
        },
        notify: function() {
            this.debug('Environment', 'notify()', 4);
            messnamed('OOVU_UPDATE', 'bang');
        },
        register_audio_node_address: function(
            osc_address, 
            audio_node_type, 
            object_dict_address) {
            this.debug('Environment', 'register_audio_node_address()', 12);
            environment_dict.replace(Environment.join([
                audio_node_type + '-addresses',
                osc_address]),
                object_dict_address);
        },
        register_osc_address: function(osc_address, object_id) {
            this.debug('Environment', 'register_osc_address()', 12);
            var re = /\//g;
            var massaged_address = osc_address.substring(1).replace(re, '::');
            environment_dict.replace(Environment.join([
                'osc-addresses',
                massaged_address,
                '.']),
                object_id);
        },
        setup_common_interface: function(object_dict, object_id, meta_array) {
            this.debug('Environment', 'setup_common_interface()', 8);
            this.add_interface(object_id, object_dict, 'common', 'dumpmeta');
            this.add_interface(object_id, object_dict, 'common', 'getid');
            this.add_interface(object_id, object_dict, 'common', 'getinterface');
            this.add_interface(object_id, object_dict, 'common', 'getmeta');
            this.add_interface(object_id, object_dict, 'common', 'getname');
            this.add_interface(object_id, object_dict, 'common', 'show');
            meta_array.push('getname');
        },
        unregister_audio_node_address: function(
            osc_address, 
            audio_node_type) {
            this.debug('Environment', 'unregister_audio_node_address()', 12);
            if (osc_address === Environment.NULL) {
                return;
            }
            environment_dict.remove(Environment.join([
                audio_node_type + '-addresses',
                osc_address]));
        },
        unregister_osc_address: function(osc_address) {
            this.debug('Environment', 'unregister_osc_address()', 8);
            if (osc_address === Environment.NULL) {
                return;
            }
            var dict_address_base = ['osc-addresses'];
            var osc_address_parts = osc_address.substring(1).split('/').concat(
                ['.']);
            var dict_address = Environment.join(dict_address_base.concat(osc_address_parts));
            environment_dict.remove(dict_address);
            osc_address_parts.pop();
            while (osc_address_parts.length) {
                dict_address = Environment.join(dict_address_base.concat(osc_address_parts));
                var subdict = environment_dict.get(dict_address);
                if (subdict !== null) {
                    if (Environment.get_keys_safely(subdict).length) {
                        break;
                    }
                }
                environment_dict.remove(dict_address);
                osc_address_parts.pop();
            }
        },
        validate_id: function(id) {
            if (id === undefined || 
                id === 'UndefinedID') {
                throw 'Undefined ID: ' + id;
            } else if (isNaN(parseInt(id, 10), 10)) {
                throw 'Bad ID number: ' + id;
            }
            id = parseInt(id, 10);
            if (id < 1000) {
                throw 'Unlikely Max ID: ' + id;
            }
            return id;
        },
        validate_name: function(name) {
            if (name === undefined || 
                name === 'UndefinedName' ||
                name === 0) {
                throw 'Undefined name: ' + name;
            }
            return name;
        },
        validate_osc_address: function(osc_address) {
            if (osc_address === undefined || 
                osc_address === 'UndefinedOSCAddress' ||
                osc_address === 0) {
                throw 'Undefined OSC address: ' + osc_address;
            }
            return osc_address;
        }
    };

////////////////////
/** NODE CLASSES **/
////////////////////

function Attribute(module, attribute_id) {

    /** PROPERTIES **/

    this.module = module; 

    this.attribute_id = attribute_id;

    this.dict_address = Environment.join([
        this.module.dict_address,
        'attributes::objects',
        attribute_id + '-attribute'
        ]);

    /** METHODS **/

    this.allocate = function(patcherargs_dict, member_type) {
        Environment.debug('Attribute', 'allocate()', 4);
        var exists = environment_dict.contains(this.dict_address);
        if (!exists) {
            var attribute_dict = new Dict();
            var INNER_DICT = new Dict();
            var meta_array = new Array();
            attribute_dict.set('name', Environment.NULL);
            attribute_dict.set('id', this.attribute_id);
            attribute_dict.set('interface', INNER_DICT);
            Environment.setup_common_interface(
                attribute_dict, 
                this.attribute_id, 
                meta_array
                );
            this.setup_data_type(
                attribute_dict, 
                this.attribute_id, 
                patcherargs_dict, 
                meta_array
                );
            this.setup_member_type(
                attribute_dict, 
                this.attribute_id, 
                patcherargs_dict, 
                member_type, 
                meta_array
                );
            attribute_dict.set('patcherargs', patcherargs_dict);
            meta_array.push(Environment.NULL);
            attribute_dict.set('meta', meta_array);
            environment_dict.replace(this.dict_address, attribute_dict);
            attribute_dict.clear();
            attribute_dict.freepeer();
            INNER_DICT.freepeer();
        }
        return this;
    };

    this.deallocate = function() {
        Environment.debug('Attribute', 'deallocate()', 4);
        this.unregister_name();
        environment_dict.remove(this.dict_address);
        return this;
    };

    this.get = function(key) {
        Environment.debug('Attribute', 'get()', 8);
        return environment_dict.get(Environment.join([
            this.dict_address, key]));
    };

    this.get_name = function() {
        Environment.debug('Attribute', 'get_name()', 8);
        return environment_dict.get(Environment.join([
            this.dict_address, 'name']));
    };

    this.get_osc_address = function() {
        Environment.debug('Attribute', 'get_osc_address()', 8);
        var this_name = this.get_name();
        var module_name = this.module.get_name();
        if (this_name !== Environment.NULL &&
            module_name !== Environment.NULL) {
            return '/' + module_name + '/' + this_name;
        }
        return Environment.NULL;
    };

    this.register_name = function(desired_name) {
        Environment.debug('Attribute', 'register_name()', 4);
        var current_name = this.get_name();
        if (current_name === desired_name) {
            return;
        }
        // unregister current name
        // acquire a new unique name
        // set own name reference to acquired name
        // register acquired name in parent module
        // register new osc address
        this.unregister_name();
        var acquired_name = Environment.find_unique_name(
            desired_name, environment_dict.get(Environment.join([
                this.module.dict_address, 'attributes::names'])));
        environment_dict.replace(Environment.join([
            this.dict_address, 'name']), acquired_name);
        environment_dict.replace(Environment.join([
            this.module.dict_address, 'attributes::names', acquired_name]),
            this.attribute_id);
        this.register_osc_address();
    };

    this.register_osc_address = function() {
        var osc_address = this.get_osc_address();
        if (osc_address !== Environment.NULL) {
            Environment.register_osc_address(osc_address, this.attribute_id);
        }
    }

    this.setup_data_type = function(
        attribute_dict,
        attribute_id,
        patcherargs_dict,
        meta_array) {
        Environment.debug('Attribute', 'setup_data_type()', 8);
        var data_type = null;
        if (patcherargs_dict.contains('datatype')) {
            data_type = patcherargs_dict.get('datatype');
        }
        switch (data_type) {
            case 'boolean':
                Environment.add_interface(attribute_id, attribute_dict, 
                    'boolean', 'toggle');
                break;

            // arrays
            case 'floatarray':
            case 'intarray':
                this.setup_array_interface(
                    attribute_dict,
                    attribute_id,
                    patcherargs_dict,
                    meta_array);
                break;

            case 'option':
                Environment.add_interface(attribute_id, attribute_dict, 
                    'option', 'getoptions');
                Environment.add_interface(attribute_id, attribute_dict, 
                    'option', 'options');
                var options = [];
                if (patcherargs_dict.contains('options')) {
                    options = patcherargs_dict.get('options');
                }
                attribute_dict.set('options', options);
                meta_array.push('getoptions');

            // no configuration
            case 'generic':
            case 'string':
            case 'oscpath':
            case 'filesystempath':
                break;

            // addressed
            case 'pull':
                Environment.add_interface(attribute_id, attribute_dict, 
                    'pull', 'getaddresses');
                meta_array.push('getaddresses');
                break;
            case 'push':
                Environment.add_interface(attribute_id, attribute_dict, 
                    'push', 'getaddresses');
                meta_array.push('getaddresses');
                break;

            // extremum
            case 'range':
                Environment.add_interface(attribute_id, attribute_dict, 
                    'range', 'center');
                Environment.add_interface(attribute_id, attribute_dict, 
                    'range', 'width');
            case 'decimal':
            case 'integer':
                this.setup_extremum_interface(
                    attribute_dict,
                    attribute_id,
                    patcherargs_dict,
                    meta_array);
                break;

            // fallback
            case null:
                data_type = 'generic';
                break;
            default:
                error('Unknown datatype: ' + data_type + '\n');
                data_type = 'generic';
                break;
        }
        attribute_dict.set('datatype', data_type);
        Environment.add_interface(attribute_id, attribute_dict, 
            'attribute', 'getdatatype');
    };

    this.setup_member_type = function(
        attribute_dict,
        attribute_id,
        patcherargs_dict,
        member_type,
        meta_array) {
        Environment.debug('Attribute', 'setup_member_type()', 8);
        switch (member_type) {
            case 'method':
                attribute_dict.set('membertype', 'method');
                break;
            case 'property':
            case null:
                this.setup_property_interface(
                    attribute_dict,
                    attribute_id,
                    patcherargs_dict,
                    member_type,
                    meta_array
                    );
                break;
            default:
                error('Unknown membertype: ' + member_type + '\n');
                this.setup_property_interface(
                    attribute_dict,
                    attribute_id,
                    patcherargs_dict,
                    member_type,
                    meta_array
                    );
        }
        Environment.add_interface(attribute_id, attribute_dict, 
            'attribute', 'getmembertype');
        var default_value = Environment.NULL;
        if (patcherargs_dict.contains('default')) {
            default_value = patcherargs_dict.get('default');
        }
        attribute_dict.set('default', default_value);
    };

    this.setup_array_interface = function(
        attribute_dict,
        attribute_id,
        patcherargs_dict,
        meta_array) {
        var length = 1;
        if (patcherargs_dict.contains('length')) {
            length = patcherargs_dict.get('length');
        }
        Environment.add_interface(attribute_id, attribute_dict, 
            'array', 'getlength');
        Environment.add_interface(attribute_id, attribute_dict, 
            'array', 'length');
        attribute_dict.set('length', length); 
        meta_array.push('getlength');
    };

    this.setup_extremum_interface = function(
        attribute_dict,
        attribute_id,
        patcherargs_dict,
        meta_array) {
        var range = [0., 1.];
        if (patcherargs_dict.contains('range')) {
            var range_temp = patcherargs_dict.get('range');
            if (range_temp instanceof Array && range_temp.length == 2) {
                if (typeof range_temp[0] === 'number' || 
                    range_temp[0] === Environment.NULL) {
                    range[0] = range_temp[0];
                }
                if (typeof range_temp[1] === 'number' || 
                    range_temp[1] === Environment.NULL) {
                    range[1] = range_temp[1];
                }
            }
        }
        Environment.add_interface(attribute_id, attribute_dict, 
            'extremum', 'getrange');
        Environment.add_interface(attribute_id, attribute_dict, 
            'extrumem', 'range');
        attribute_dict.set('range', range); 
        meta_array.push('getrange');
    };

    this.setup_property_interface = function(
        attribute_dict,
        attribute_id,
        patcherargs_dict,
        member_type,
        meta_array) {
        attribute_dict.set('membertype', 'property');
        Environment.add_interface(attribute_id, attribute_dict, 
            'value', 'getvalue');
        Environment.add_interface(attribute_id, attribute_dict, 
            'value', 'getpriority');
        var priority = 0;
        if (patcherargs_dict.contains('priority')) {
            try {
                priority = parseInt(
                    patcherargs_dict.get('priority'), 10);
            } catch (e) {
                error(e);
            }
        }
        attribute_dict.set('priority', priority);
        meta_array.push('getvalue');
    };

    this.unregister_name = function() {
        Environment.debug('Attribute', 'unregister_name()', 8);
        // remove osc address
        // nullify own name reference
        // remove name from parent module's namespace
        this.unregister_osc_address();
        var current_name = this.get_name();
        if (current_name !== Environment.NULL) {
            environment_dict.replace(Environment.join([
                this.dict_address, 'name']), Environment.NULL);
            environment_dict.remove(Environment.join([
                this.module.dict_address,
                'attributes::names',
                current_name]));
        }
    };

    this.unregister_osc_address = function() {
        var osc_address = this.get_osc_address();
        if (osc_address !== Environment.NULL) {
            Environment.unregister_osc_address(osc_address);
        }
    }
}

function Module(module_id) {

    /** PROPERTIES **/

    this.module_id = module_id;

    this.dict_address = Environment.join([
        'modules::objects', 
        this.module_id + '-module'
        ]);

    /** METHODS **/

    this.allocate = function() {
        Environment.debug('Module', 'allocate()', 4);
        var exists = environment_dict.contains(this.dict_address);
        if (!exists) {
            var module_dict = new Dict();
            var INNER_DICT = new Dict();
            var meta_array = new Array();
            module_dict.set('name', Environment.NULL);
            module_dict.set('id', this.module_id);
            module_dict.replace('attributes::names', INNER_DICT); 
            module_dict.replace('attributes::objects', INNER_DICT);
            module_dict.replace('audio-nodes::names', INNER_DICT);
            module_dict.replace('audio-nodes::objects', INNER_DICT);
            module_dict.replace('interface', INNER_DICT);
            Environment.setup_common_interface(
                module_dict, this.module_id, meta_array);
            Environment.add_interface(module_id, module_dict, 
                'module', 'getproperties');
            module_dict.replace('reference_count', 0);
            meta_array.push(Environment.NULL);
            module_dict.replace('meta', meta_array);
            environment_dict.replace(this.dict_address, module_dict);
            module_dict.clear();
            module_dict.freepeer();
            INNER_DICT.freepeer();
        }
        return this;
    };

    this.deallocate = function() {
        Environment.debug('Module', 'deallocate()', 4);
        this.unregister_name();
        environment_dict.remove(this.dict_address);
        return this;
    };

    this.decrement_reference_count = function() {
        Environment.debug('Module', 'decrement_reference_count()', 4);
        return this.set_reference_count(this.get_reference_count() - 1);
    };

    this.get_attributes = function() {
        Environment.debug('Module', 'get_attributes()', 8);
        var attribute_objects_dict = environment_dict.get(Environment.join([
            this.dict_address, 'attributes::objects']));
        var attribute_object_keys = Environment.get_keys_safely(
            attribute_objects_dict);
        var attributes = [];
        for (var i = 0, j = attribute_object_keys.length; i < j; i++) {
            var attribute_id = environment_dict.get(Environment.join([
                this.dict_address, 
                'attributes::objects', 
                attribute_object_keys[i],
                'id']));
            attributes.push(new Attribute(this, attribute_id));
        }
        return attributes;
    };

    this.get_audio_nodes = function() {
        Environment.debug('Module', 'get_audio_nodes()', 8);
        var audio_nodes_dict = environment_dict.get(Environment.join([
            this.dict_address, 'audio-nodes::objects']));
        var audio_nodes_keys = Environment.get_keys_safely(audio_nodes_dict);
        var audio_nodes = [];
        for (var i = 0, j = audio_nodes_keys.length; i < j; i++) {
            var audio_node_id = environment_dict.get(Environment.join([
                this.dict_address,
                'audio-nodes::objects',
                audio_nodes_keys[i],
                'id']));
            var audio_node_type = environment_dict.get(Environment.join([
                this.dict_address,
                'audio-nodes::objects',
                audio_nodes_keys[i],
                'type']));
            var audio_node = new AudioNode(
                this, audio_node_id, audio_node_type);
            audio_nodes.push(audio_node);
        } 
        return audio_nodes;
    };

    this.get_children = function() {
        var attributes = this.get_attributes();
        var audio_nodes = this.get_audio_nodes();
        return attributes.concat(audio_nodes);
    }

    this.get_name = function() {
        Environment.debug('Module', 'get_name()', 8);
        return environment_dict.get(Environment.join([
            this.dict_address, 'name']));
    };

    this.get_osc_address = function() {
        Environment.debug('Module', 'get_osc_address()', 8);
        var this_name = this.get_name();
        if (this_name !== Environment.NULL) {
            return '/' + this_name;
        }
        return Environment.NULL;
    };

    this.get_properties_by_priority = function() {
        Environment.debug('Module', 'get_attributes_by_priority()', 8);
        var attributes = this.get_attributes();
        var ordered_properties = [];
        var priorities = [];
        var properties_by_priority = {};
        for (var i = 0, j = attributes.length; i < j; i++) {
            var membertype = attributes[i].get('membertype');
            if (membertype === 'property') {
                var priority = attributes[i].get('priority');
                if (priorities.indexOf(priority) === -1) {
                    priorities.push(priority);
                    properties_by_priority[priority] = [];
                }
                properties_by_priority[priority].push(attributes[i]);
            }
        }
        priorities.sort(function(a, b) { return a - b }).reverse();
        for (var i = 0, j = priorities.length; i < j; i++) {
            var priority = priorities[i];
            var properties = properties_by_priority[priority].sort(
                function(a, b) {
                    return a.get_name().localeCompare(b.get_name());
                });
            for (var x = 0, y = properties.length; x < y; x++) {
                ordered_properties.push(properties[x]);
            }
        }
        return ordered_properties;
    };

    this.get_reference_count = function() {
        Environment.debug('Module', 'get_reference_count()', 8);
        var reference_count_dict_address = Environment.join([
            this.dict_address, 'reference_count']);
        return environment_dict.get(reference_count_dict_address);
    };

    this.increment_reference_count = function() {
        Environment.debug('Module', 'increment_reference_count()', 4);
        return this.set_reference_count(this.get_reference_count() + 1);
    };

    this.register_name = function(desired_name) {
        // unregister
        // acquire new name
        // register acquired name in environment
        // register acquired name in local reference
        // reregister all member osc addresses
        Environment.debug('Module', 'register_name()', 4);
        var current_name = this.get_name();
        if (current_name === desired_name) {
            var osc_address = this.get_osc_address();
            if (!environment_dict.contains(Environment.join([
                'osc-addresses', osc_address]))) {
                Environment.register_osc_address(osc_address, this.module_id);    
            }
            return;
        }
        this.unregister_name();
        var acquired_name = Environment.find_unique_name(
            desired_name, environment_dict.get('modules::names'));
        environment_dict.replace(Environment.join([
            this.dict_address, 'name']), 
            acquired_name);
        environment_dict.replace(Environment.join([
            'modules::names', acquired_name]),
            this.module_id);
        Environment.register_osc_address(
            this.get_osc_address(), this.module_id);
        var children = this.get_children();
        for (var i = 0, j = children.length; i < j; i++) {
            children[i].register_osc_address();
        }
    };

    this.register_osc_address = function() {
        var osc_address = this.get_osc_address(); 
        if (osc_address !== Environment.NULL) {
            Environment.register_osc_address(osc_address, this.module_id);
        }
    };

    this.set_reference_count = function(value) {
        Environment.debug('Module', 'set_reference_count()', 8);
        var reference_count_dict_address = Environment.join([
            this.dict_address, 'reference_count']);
        environment_dict.replace(reference_count_dict_address, value);
        return value;
    };
    
    this.unregister_name = function() {
        Environment.debug('Module', 'unregister_name()', 8);
        Environment.unregister_osc_address(this.get_osc_address());
        var children = this.get_children();
        for (var i = 0, j = children.length; i < j; i++) {
            children[i].unregister_osc_address();
        }
        var current_name = this.get_name();
        environment_dict.replace(Environment.join([
            this.dict_address, 'name']), Environment.NULL);
        if (environment_dict.contains(
            Environment.join(['modules::names', current_name]))) {
            environment_dict.remove(Environment.join([
                'modules::names', current_name]));
        }
    };

    this.unregister_osc_address = function() {
        var osc_address = this.get_osc_address(); 
        if (osc_address !== Environment.NULL) {
            Environment.unregister_osc_address(osc_address);
        }
    };
}

function AudioNode(module, audio_node_id, audio_node_type) {

    this.module = module; 
    this.audio_node_id = audio_node_id;
    this.audio_node_type = audio_node_type;
    this.dict_address = Environment.join([
        this.module.dict_address,
        'audio-nodes::objects',
        audio_node_id + '-audio-node'
        ]);

    this.allocate = function() {
        Environment.debug('AudioNode', 'allocate()', 4);
        if (!environment_dict.contains(this.dict_address)) {
            var audio_node_dict = new Dict();
            audio_node_dict.set('name', Environment.NULL);
            audio_node_dict.set('id', this.audio_node_id);
            audio_node_dict.set('type', this.audio_node_type);
            environment_dict.replace(this.dict_address, audio_node_dict);
            audio_node_dict.clear();
            audio_node_dict.freepeer();
        }
        return this;
    };

    this.deallocate = function() {
        Environment.debug('AudioNode', 'deallocate()', 4);
        this.unregister_name();
        environment_dict.remove(this.dict_address);
        return this;
    };

    this.get_name = function() {
        Environment.debug('AudioNode', 'get_name():\t' + this.dict_address, 8);
        return environment_dict.get(Environment.join([
            this.dict_address, 'name']));
    };

    this.get_osc_address = function() {
        Environment.debug('AudioNode', 'get_osc_address()', 8);
        var this_name = this.get_name();
        var module_name = this.module.get_name();
        if (this_name !== Environment.NULL &&
            module_name !== Environment.NULL) {
            return '/' + module_name + '/' + this_name;
        }
        return Environment.NULL;
    };

    this.register_name = function(desired_name) {
        Environment.debug('AudioNode', 'register_name()', 4);
        var current_name = this.get_name();
        desired_name = this.audio_node_type + '/' + desired_name;
        if (current_name === desired_name) {
            return;
        }
        this.unregister_name();
        var acquired_name = Environment.find_unique_name(
            desired_name, environment_dict.get(Environment.join([
                this.module.dict_address,
                'audio-nodes::names'
                ])));
        environment_dict.replace(Environment.join([
            this.dict_address, 'name']), acquired_name);
        environment_dict.replace(Environment.join([
            this.module.dict_address, 
            'audio-nodes::names', acquired_name]),
            this.audio_node_id);
        this.register_osc_address();
    };

    this.register_osc_address = function() {
        Environment.debug('AudioNode', 'unregister_osc_address()', 8);
        var osc_address = this.get_osc_address();
        if (osc_address !== Environment.NULL) {
            Environment.register_audio_node_address(
                osc_address, this.audio_node_type, this.dict_address);
        }
    };

    this.unregister_name = function() {
        Environment.debug('AudioNode', 'unregister_name()', 8);
        var current_name = this.get_name();
        this.unregister_osc_address();
        if (current_name !== Environment.NULL) {
            environment_dict.replace(Environment.join([
                this.dict_address, 'name']), Environment.NULL);
            environment_dict.remove(Environment.join([
                this.module.dict_address,
                'audio-nodes::names',
                current_name]));
        }
    };
    
    this.unregister_osc_address = function() {
        var osc_address = this.get_osc_address();
        Environment.unregister_audio_node_address(
            osc_address, this.audio_node_type);
    };
}

Attribute.local = 1;
AudioNode.local = 1;
Module.local = 1;
Environment.local = 1;

////////////////////////
/** PUBLIC FUNCTIONS **/
////////////////////////

function bind_global(
    global_osc_address) {
    var start_time = Date.now();
    try {
        Environment.validate_osc_address(global_osc_address);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'bind_global(): ' + global_osc_address, 0);
    var object_id = Environment.NULL;
    var osc_parts = global_osc_address.substring(1).split('/').concat(['.']);
    var address_parts = ['osc-addresses'];
    while (osc_parts.length) {
        address_parts.push(osc_parts.shift());
        var dict_address = Environment.join(address_parts);
        if (!environment_dict.contains(dict_address)) {
            Environment.debug('JS', 'FAILED ' + dict_address, 4, true);
            outlet(1, false);
            return;
        } else if (!osc_parts.length) {
            object_id = environment_dict.get(dict_address);
            Environment.debug('JS', 'bound ' + dict_address, 4);
            break;
        }
    }
    outlet(1, true);
    outlet(0, object_id);
    Environment.debug_finished(
        'JS: bind_global()', 
        start_time, 
        Date.now()
        );
}

function bind_local(
    module_id, 
    local_osc_address) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
        Environment.validate_osc_address(local_osc_address);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug(
        'JS', 
        'bind_local(): ' + module_id + ' ' + local_osc_address,
        0);
    var address = Environment.join([
        'modules::objects',
        module_id + '-module',
        'attributes::names', 
        local_osc_address
        ]);
    var object_id = Environment.NULL;
    if (environment_dict.contains(address)) {
        object_id = environment_dict.get(address);
        Environment.debug(
            'JS', 
            'bound ' + module_id + ' ' + local_osc_address, 
            4);
    } else if (local_osc_address == '.') {
        object_id = module_id;
        Environment.debug(
            'JS', 
            'bound ' + module_id + ' ' + local_osc_address, 
            4);
    } else {
        Environment.debug(
            'JS', 
            'FAILED ' + module_id + ' ' + local_osc_address, 
            4,
            true);
            outlet(1, false);
    }
    outlet(1, true);
    outlet(0, object_id);
    Environment.debug_finished(
        'JS bind_local()', 
        start_time, 
        Date.now()
        );
}

function construct_attribute(
    module_id,
    attribute_id,
    desired_name,
    member_type,
    patcherargs_dict_name) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
        Environment.validate_id(attribute_id);
        Environment.validate_name(desired_name);
        Environment.validate_id(jsarguments[1]);
        Environment.validate_name(patcherargs_dict_name);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'construct_attribute(): ' + desired_name, 0);
    var module = new Module(module_id).allocate();
    var patcherargs_dict = new Dict(patcherargs_dict_name);
    var attribute = new Attribute(module, attribute_id).allocate(
        patcherargs_dict, member_type);
    if (patcherargs_dict.contains('substitutions')) {
        var substitutions = patcherargs_dict.get('substitutions');
        if (typeof substitutions != 'array') {
            substitutions = [substitutions];
        }
        for (var i = 0, j = substitutions.length; i < j; i++) {
            desired_name = desired_name.replace('{}', substitutions[i]);
        }
    }
    var acquired_name = attribute.register_name(desired_name);
    attribute.module.increment_reference_count();
    Environment.debug('...', jsarguments[1] + '-allocated', 4);
    messnamed(jsarguments[1] + '-allocated',
        'attribute',
        module.module_id,
        attribute.attribute_id);
    Environment.debug('...', jsarguments[1] + '-this', 4);
    messnamed(jsarguments[1] + '-this',
        attribute.dict_address);
    Environment.debug('...', jsarguments[1] + '-datatype', 4);
    messnamed(jsarguments[1] + '-datatype', 
        attribute.dict_address + '::datatype');
    Environment.debug('...', jsarguments[1] + '-membertype', 4);
    messnamed(jsarguments[1] + '-membertype',
        attribute.dict_address + '::membertype');
    Environment.debug('...', 'default value', 4);
    var default_value = environment_dict.get(Environment.join([
        attribute.dict_address, 'default']));
    outlet(1, true);
    if (default_value !== Environment.NULL) {
        outlet(0, default_value);
    }
    Environment.notify();
    Environment.debug_finished(
        'JS construct_attribute()', 
        start_time, 
        Date.now()
        );
}

function construct_audio_node(
    module_id,
    audio_node_id,
    desired_name,
    audio_node_type) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
        Environment.validate_id(audio_node_id);
        Environment.validate_name(desired_name);
        Environment.validate_name(audio_node_type);
        Environment.validate_id(jsarguments[1]);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'construct_audio_node(): ' + desired_name, 0);
    var module = new Module(module_id).allocate();
    var audio_node = new AudioNode(
        module, audio_node_id, audio_node_type).allocate();
    var acquired_name = audio_node.register_name(desired_name);
    audio_node.module.increment_reference_count();
    outlet(1, true);
    outlet(0, audio_node_id);
    Environment.notify();    
    Environment.debug_finished(
        'JS construct_audio_node()',
        start_time, 
        Date.now()
        );
}

function construct_module(
    module_id,
    desired_name) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
        Environment.validate_name(desired_name);
        Environment.validate_id(jsarguments[1]);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'construct_module(): ' + desired_name, 0);
    var module = new Module(module_id).allocate();
    var acquired_name = module.register_name(desired_name);
    module.increment_reference_count();
    messnamed(jsarguments[1] + '-allocated',
        'module',
        module.module_id);
    messnamed(jsarguments[1] + '-this', module.dict_address);
    outlet(1, true);
    Environment.notify();
    Environment.debug_finished(
        'JS construct_module()',
        start_time, 
        Date.now()
        );
}

function destruct_attribute(
    module_id,
    attribute_id) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
        Environment.validate_id(attribute_id);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'destruct_attribute()', 0);
    var module = new Module(module_id).allocate();
    var attribute = new Attribute(module, attribute_id)
    var attribute_name = attribute.get_name();
    attribute.deallocate();
    if (attribute.module.decrement_reference_count() < 1) {
        attribute.module.deallocate();
    }
    outlet(1, true);
    Environment.notify();
    Environment.debug('JS', 'destructed ' + attribute_name, 4);
    Environment.debug_finished(
        'JS destruct_attribute()',
        start_time, 
        Date.now()
        );
}

function destruct_audio_node(
    module_id,
    audio_node_id,
    audio_node_type) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
        Environment.validate_id(audio_node_id);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'destruct_audio_node()', 0);
    var module = new Module(module_id).allocate();
    var audio_node = new AudioNode(
        module, audio_node_id, audio_node_type).deallocate();
    if (audio_node.module.decrement_reference_count() < 1) {
        audio_node.module.deallocate();
    }
    outlet(1, true);
    Environment.notify();
    Environment.debug_finished(
        'JS destruct_audio_node()',
        start_time, 
        Date.now()
        );
}
 
function destruct_module(
    module_id) {
    var start_time = Date.now();
    if (Environment.is_hash_variable(module_id)) {
        return;
    }
    try {
        Environment.validate_id(module_id);
    } catch (e) {
        error(e);
        outlet(1, false);
        return;
    }
    Environment.debug('JS', 'destruct_module()', 0);
    var module = new Module(module_id).allocate();
    var module_name = module.get_name();
    if (module.decrement_reference_count() < 1) {
        module.deallocate();
    }
    outlet(1, true);
    Environment.notify();
    Environment.debug('JS', 'destructed ' + module_name, 4);
    Environment.debug_finished(
        'JS destruct_module()',
        start_time, 
        Date.now()
        );
}

function get_state() {
    var commands = [];
    commands.push.apply(commands, [
        'wclose', 'clear',
        '###################', 'cr',
        'CUE \"My Event Name\"', 'cr', 
        '###################', 'cr'
        ]);
    var modules = Environment.get_modules();
    for (var i = 0, j = modules.length; i < j; i++) {
        var module = modules[i];
        var module_osc_address = module.get_osc_address();
        var properties = module.get_properties_by_priority();
        if (!properties.length) {
            continue;
        }
        commands.push.apply(commands, [
            'cr', 'tab', '### ' + module_osc_address + ' ###', 'cr', 'cr'
            ]);
        for (var x = 0, y = properties.length; x < y; x++) {
            var property = properties[x];
            var property_osc_address = property.get_osc_address();
            var property_value = environment_dict.get(Environment.join([
                property.dict_address, 'value']));
            if (property_value !== Environment.NULL && 
                property_value !== null) {
                commands.push('tab');
                commands.push(property_osc_address);
                if (typeof property_value === 'string') {
                    commands.push('\"' + property_value + '\"');
                } else {
                    commands.push(property_value);
                }
                commands.push('cr');
            }
        }
    }
    commands.push('open');
    for (var i = 0, j = commands.length; i < j; i++) {
        outlet(0, commands[i]);
    }
}

