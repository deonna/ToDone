# Pre-work - *toDone*

**toDone** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Deonna Hodges**

Time spent: **28** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides
.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Added [ButterKnife](https://github.com/JakeWharton/butterknife) library to reduce boilerplate code
* [x] Added ability to filter by completeness levels (all, incomplete, and complete)
* [x] Added a splash page to the app
* [x] Added dynamic sorting by priority (highest to lowest) when todos' priority is edited or a new todo is added
* [x] Added dynamic UI updating upon selecting a due date when edit dialog is closed
* [x] Added dynamic UI updating upon selecting a priority (by clicking on stars) when edit dialog is opened
* [x] Added ability for user to mark items as 'complete' using a checkbox, including a strikethrough for visual feedback to the user
* [x] Added custom styles for checkbox, splash screen, ActionBar, Edit Dialog, etc. + iconography
* [x] Added material design DatePicker from [BetterPickers](https://github.com/code-troopers/android-betterpickers) library for selecting due dates
* [x] Added soft keyboard hiding after edit dialog is saved
* [x] Implemented modal dialogs for edit Dialog and due date selection dialog
* [x] Implemented ViewHolder pattern to reuse View containers
* [x] Implemented Parcelable for Todo items

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/Tvfpr2A.gif' width='' alt='Video Walkthrough of toDone' />

[toDone Latest Demo](http://i.imgur.com/Tvfpr2A.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

Previous demos:

[toDone Demo #2](http://i.imgur.com/ICXmNDy.gif)

[toDone Demo #1](http://i.imgur.com/OyLMmLd.gif)

## Notes

Describe any challenges encountered while building the app:

- The UI was time consuming for me. Even though I have little to no design training besides the
tricks + inspiration I pick up on blogs and design sites, I
appreciate good design and tweaking the UI when something looks 'off' is a task I feel strongly compelled
to do. I spent a good amount of time creating a basic mockup and tweaking it so that everything
was well positioned.

- Communication between fragments was also a challenge. I felt I was doing this in somewhat of a
hack-y way, because there seem to be many options (leveraging the FragmentManager's ability to
get all fragments, using Bundle,
using custom event listeners, using the backstack [which I didn't experiment with, but learned
about after reading a few StackOverflow posts.

- I tried to keep myself honest when it came to writing small commits, creating feature branches,
readable commit messages, and opening pull requests, even though it was solo project
(which is hard when no one is going to look at your project after it's completed).

There were a couple of areas I thought would be challenging, but turned out not to be:

- SQLite DB configuration and transactions were surprisingly simple, as well as creating a custom
ArrayAdapter. The Android Activity lifecycle and figuring out which methods I needed to hook into
 wasn't difficult to figure out, either.

Things I loved:

- Wow. ButterKnife tremendously reduced the amount of boilerplate code I wrote and including it
strengthened the overall architecture of the application to be better encapsulated.

## License

    Copyright [2017] [Deonna Hodges]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.