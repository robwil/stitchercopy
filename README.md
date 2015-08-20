# StitcherCopy

The purpose of this app is to use root access on Android to automate something I found myself doing quite often:

- Extracting all the *.audio files from the Stitcher download directory (any files downloaded for "Listen Later" go there)
- Copy to external SD card, into the "audio" subfolder, renaming into mp3.

I did this manually one too many times, so decided to automate it. The code is super hard-coded to my use case, but should be easily forkable for others' similar needs.

*Room for improvement:* Get root permission once for the app and then reuse that for future uses (currently it asks for root access for every launch)