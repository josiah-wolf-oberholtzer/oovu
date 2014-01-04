`oovu`
======

A modular performance framework for Max 6.

![OOVU](https://raw.github.com/josiah-wolf-oberholtzer/oovu/master/misc/oovu.png "OOVU")

[Introduction to OOVU (vimeo.com)](https://vimeo.com/83364622)

What is it?
-----------

`oovu` is a collection of externals and patchers for Max 6. Open source, mostly
written in Java. Inspired by Jamoma.

What does it do?
----------------

- `oovu` allows you to quickly create structured patches.
- `oovu` organizes its components into hierarchical modules where every
  component is addressable via OSC from anywhere in the patch.
- `oovu` components support a variety of messages, and can be bound to MIDI and
  keyboard events, as well as to other components and even to automated
  patterns.
- `oovu` also allows for patching-free audio routing, and automatically handles
  up- and down-mixing between modules with different numbers of channels.
- `oovu` helps you keep each module's logic completely separate from its user
  interface.



Installation
------------

Just copy the entire `oovu` project into your Max 6 `packages` directory.

Start patching
--------------

Open the `oovu-overview` from Max's `extras` menu, and start exploring.

Or right-click in any patcher and `Paste From` > `oovu`.
