# Account Verification with Servlets and Twilio

[![Build Status](https://travis-ci.org/TwilioDevEd/account-verification-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/account-verification-servlets)

When a new user signs up for your application, you want to make sure their contact information is accurate. You'd also like some assurance they are in fact a human being! You want to make sure that every new user account in your system is an actual person you can serve.

There are many layers of security you can put in place to increase the quality of your signups, but one of the best is account verification via SMS. Before a registration is fully completed, your application sends the user a one-time passcode via SMS. The user then enters the code on your website to complete their registration.

In this tutorial, you'll learn how to implement account verification at the point of registration using Twilio-powered Authy.

## Local Development

This project is build using [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) and uses [PostgreSQL](http://www.postgresql.org) as database.

1. First clone this repository and `cd` into it.

   ```bash
   $ git clone git@github.com:TwilioDevEd/account-verification-servlets.git
   $ cd account-verification-servlets
   ```

1. Copy the sample configuration file and edit it to match your configuration.

   ```bash
   $ cp .environment .env
   ```

   You can find your `TWILIO_ACCOUNT_SID` and `TWILIO_AUTH_TOKEN` in your
   [Twilio Account Settings](https://www.twilio.com/user/account/settings).
   You will also need a `TWILIO_NUMBER`, which you may find [here](https://www.twilio.com/user/account/phone-numbers/incoming).
   The `AUTHY_API_KEY` can be found [here](https://dashboard.authy.com/).

   Run `source .env` to export the environment variables.

1. Create the database.

   ```bash
   $ createdb account_verification_servlets
   ```

1. Execute the migrations.

   ```bash
   $ ./gradlew flywayMigrate
   ```

1. Make sure the tests succeed.

   ```bash
   $ ./gradlew check
   ```

1. Start the server.

   ```bash
   $ ./gradlew jettyRun
   ```

1. Check it out at [http://localhost:8080](http://localhost:8080).

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
