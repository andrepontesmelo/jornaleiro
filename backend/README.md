# About

Jornaleiro is a search engine of Brazilian official gazettes also known as public journals.

This is the Java backend that makes a facade for currently Android mobile app and AngularJS+Bootstrap web interface.

## Entities

  * ** Journal: **  Can be the country level, state level or municipal.
  * ** Session: ** A journal have multiple sessions. It is a subdivision of the journal.
  * ** Document: ** Represents a document *or a page* within one session.
  * ** Search: ** The query can be single word or a phrase.

# API

Currently, only GET operations are allowed.
All of them are designed to be safe and idempotent.
This means that any call will not cause any side effects (safe). And the effect of a single call is the same of several calls (idempotent). One benefit of this is that clients or intermediates, like proxy servers can cache results.

One specific aspect of journals is the fact that a document never expires, since it never changes since it's release. It's different from generic search engines that needs to measure the freshness of collected data.

## Call examples

```
GET /journal
GET /session
GET /document/1234
GET /search/my_phrase_or_word
```
