**VDS Vote Donate System**

Implementation of vote global and individual system with itopz donate panel task manager.
-
- Global vote reward is when players vote for the server in one of the topsites installed as a "thank you" they will receive configured item/items reward.
- Individual vote reward is when a single player (individual) vote for the server on one or more topsites and for each vote as a "thank you" he will receive configured item/items reward.

**Requirement**
- aVa Sources
- Voiced Command Handler (provided)
- Patch installation (provided)
- JDK 8
- Remove ```commons-lang3-3.0.jar``` from lib folder and from build path
- Add ```commons-lang3-3.11.jar``` on your lib folder and build path
- ```commons-lang3-3.11.jar``` is provided on releases

**Build**
- File ```VDSystem.jar```
- Version: 1.4

**Global vote system**

- IP restricted rewards
- Reward every XX minutes
- Reward step every XX votes
- Reward item list will check if item exist
- Reward item list with chances 0-100%
- Reward item randomized of min-max values
- Update console votes and ranking
- Save votes in case of restarting the server
- Monthly reset for votes
- Print response failure message
- Configured announcement print of server vote statistics

**Individual vote system**

- Automatically register .topsite command
- Reward item list will check if item exist
- Reward item list with chances 0-100%
- Reward item randomized of min-max values
- Reward will set as "expired" after 12hours checking server time
- Reward reuse 12hours
- Command used after reward will show remaining time (PM/AM) to vote again
- Check for local ips is restriction
- Check for response errors
- Check if player voted

**Donate Management** (upgrade version of [Donate Panel](https://github.com/nightw0lv/DonatePanel))

- Player donates through Donate Panel
- Item will be rewarded in game

**Configs**

- Fully configured Console
- Fully configured global reward
- Fully configured individual reward

**Console**

- Information buttons
- Can send global reward in-game manually
- Prints rewards
- Prints donates
- Prints monthly reset
- Prints database updates
- Shows server ranking statistics
- Auto scrolling messages
- Fully configured

**Installation**
![https://github.com/nightw0lv/iTopZ-Java/tree/master/HighFive/aVa/VDSystem/Patch.diff](Patch.diff)

```Special thanks to Rationale``` :cry:

**Images**
![https://prnt.sc/w6zkpg](../../images/1.png)
![https://prnt.sc/w6zl79](../../images/2.png)
![https://prnt.sc/w6zp4l](../../images/3.png)
![https://prnt.sc/w6zqok](../../images/4.png)
![https://prnt.sc/w6zuyx](../../images/5.png)
![https://prnt.sc/w6zxyo](../../images/6.png)
![https://prnt.sc/w6zzcs](../../images/7.png)

**Chancelog**

- Version 1.4
  - Update Premium Mobius Projects
    - Update JDK 17
	- Update libraries
	- Removal of 8.0 Homunculus
	- Removal of 9.0 Return of the Queen Ant
	- Removal of 5.0 Sylph
	- Addition of 8.2 Homunculus
	- Addition of 9.2 Return of the Queen Ant
	- Addition of 5.2 Frost Lord
	- Updated Patch diffs

- Version 1.3
  - Rework on VoteCMD
    - Fixed possible concurrent error on response thread (Ty Rationale)
    - Using StatsSet to transfer data in method isEligible (Ty Rationale)
    - Introducing a Flood Protector (Ty Rationale)
    - Using "NONE" for default errors to avoid NPE
    - Thread Execute will run on random between 1 and 10 seconds to avoid cheats
  - IP Flood Protection
  - Typo in Url
  - Rework Flood Protector & Individual var save method
    - Flood Protector (thanks Rationale)
      - Added FloodProtectorHolder Private class
      - Added Logic VoteSite enum
      - Added FLOOD_PROTECTOR array list
      - NOTE: HWID is not available for all packs since mostly depend on client protection, is up to server admin to add it.
    - saveIndividualVar method
      - Drops player object
      - Recount on statement vars
  - INDIVIDUAL INSERT, SELECT queries drop char_id
  - Create individual table drops char_id and primary keys
  - for existing users must drop the table (it will be automatically created again)
  - Fixing path typo in readme files
  - Update main readme with new projects
  - Add L2JDP by Elesis (Request by CrazyRXD)
  - Lucera remove redudant parenthesis
  - Mobius Premium Sylph Extra wrong library import
  - Fixing 2 errors on out of box settings
    - Default configuration
      - ArrayIndexOutOfBoundsException caused by ; in the end of reward strings
    - Database tables
      - Global table creation fixed caused by an extra , (coma)
    - Thanks LLiuKe for the heads up (y)
  - Fixing IOOBE error 
    - IOOBE error fix (thank you Huesitos)
    - Removing totally the player variable from Utilities#selectIndividualVar() method
    - missed on 133a00f
  - SQL Fix
    - Fixing individual Duplicate entry error (Thanks Francisco)
  - Fixing IP restriction after 1 player votes
    - Individual SQL select depends on ip now
    - Adding commend parameters that were missing
    - Capitalize ip to IP parameters
    - Utilities#selectIndividualVar
      - Adding IP string

- Version 1.1
  - Minimal Requirement on server resources and class consistency along projects.
    - Addition of Thread class.
    - Addition of Random class.
    - Addition of Logs class.
    - Addition of Url class.
    - Addition of Rewards class.
    - SQL independence on tables with vds_individual and vds_global tables automatic install by default
  - Addition of topsites and their configs.
    - Hopzone.
    - L2Network.
    - L2TopGameServer.
    - L2TopServers.
    - L2Votes.
    - L2JBrasil.
  - Gui
    - Addition of Debug on/off button.
    - Addition of Bug report button.
    - Removed Debug mode.
    - Rework on the class to update all topsite statistics.
    - Rework on console size and re-arrange information and console.
  - Fixed Bug that made global with individual rewards conflict.
  - Improved custom JSon parser for wannabe-called APIs on a lot of topsites.
  - Improved Debug with External IP Address.
  - Improved code style on some cases like enchanted switch on mobius projects.

- Version 1.0
  - Initial commit