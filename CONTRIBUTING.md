# When creating a pull request be sure your code is working and compiling

## Be sure your code is clean
When you made a new pull request, take a look at Codacy report: https://app.codacy.com/gh/Blackoutburst/Wally/pullRequests \
Try to minimise at the maximum amount of code issues your pull request can add if possible 0 issues should be added.

## Respect overall project coding style
You a free to do basically what you want in term of coding style but respect some basic rules such as:
- Really long line should be avoided at the maximum.
- Really long function should be avoided, use multiple small function instead on just one big chunk.
- Put space between operators and keyword to make the code easier to read e.g `if (...)` `"..." + "..."`.
- Put opening bracket on the same line e.g `if (...) {` `void foo {` `for (...) {`.
- Do not use static import as they can lead to confusion.
- Always import classes you use do not use them directly like this e.g `com.packagename.thing.Class.void(...)`.
- Inside loops if a condition is supposed to skip the current loop iteration use `if (condition) continue;`

Just look at the rest of the code you are editing to be sure everything fit correctly and does not look off compared to the rest.

## Request
Use the minimum amount of request as possible
- Api request are limited in usage per minute.
- Api request are slow and impacts the project speed a lot.

