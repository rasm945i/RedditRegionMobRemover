# Region Mob Remover
Remove monsters inside specified regions.

# Commands
- `/mobremover` - Shows available commands
- `/mobremover info` - Shows regions currently getting monsters removed
- `/mobremover reload` - Reloads the config and then reloads regions
All commands require the `regionmobremover.admin` permission!

# Dependencies
- WorldGuard (which requires WorldEdit)

# Default config example
```yaml
message:
  no-permission: "&cYou don't have permission for that!"
  show-args: "&eUse &6info&e to show enabled regions, or &6reload&e to reload configuration"
settings:
  # How often should regions be checked for entities and remove them? Default 20 ticks (1 second)
  ticks-between-removal: 20
regions:
  # World name as first key, with a list of region names in that world where monsters will be removed.
  world:
    - spawnRegion
    - shopRegion
  world_nether:
    - netherSpawnRegion
```