BukkitSettings
==============

Plugin for handling a player's settings

Permissions
===========

`setting.list`

Allow the user to use the /settings command.  Defaults to true for everyone
unless otherwise specified.

`setting.<name>`

Allows the user to view, get, and set the setting identified by *name*.
Defaults to true for everyone unless otherwise specified.  Removing this
permission sets all child settings to false (can be overrided though).

`setting.<name>.view`

Allows the user to view the setting in all BukkitSettings commands.  Defaults
to true for everyone unless otherwise specified by a parent permission node or
otherwise.

`setting.<name>.get`

Allows the user to get the value of a setting.  Inherits value from parent
permission.

`setting.<name>.set`

Allows the user to set the value of a setting.  Inherits value from parent
permission.
