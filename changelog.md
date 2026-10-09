# 0.11.0 changelog

## Fixes
- Fix deferred transfers accepting more than the storage limit in one transaction
- Fix crash when wrappers around other mods storages are used in a transaction
- Fix crash with fluid items with no tank
- Fix changes to items made by other mods being lost
- Fix get item capability make item stop stacking when empty
- Fix emptied fluid and energy items not stacking with new ones
- Fix a fluid and energy duplication issue on fabric