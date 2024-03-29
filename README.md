# Important Notice

For new development, we encourage you to use the Verify API instead of the Authy API. The Verify API is an evolution of the Authy API with continued support for SMS, voice, and email one-time passcodes, an improved developer experience and new features.

Please visit the [Verify Quickstarts Page](https://www.twilio.com/docs/verify/quickstarts) to get started with the Verify API. Thank you!

# Account Verification with Servlets and Twilio

[![Build Status](https://travis-ci.org/TwilioDevEd/account-verification-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/account-verification-servlets)

When a new user signs up for your application, you want to make sure their contact information is accurate. You'd also like some assurance they are in fact a human being! You want to make sure that every new user account in your system is an actual person and not a robot.

There are many layers of security you can put in place to increase the quality of your signups, but one of the best is an account verification via SMS. Before a registration is fully completed, your application sends the user a one-time passcode via SMS. The user then enters the code on your website to complete their registration.

In this tutorial, you'll learn how to implement account verification at the point of registration using Twilio-powered Authy.

[View the full tutorial here!](https://www.twilio.com/docs/tutorials/walkthrough/account-verification/java/servlets)

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
   [Twilio Account Settings](https://www.twilio.com/console).
   You will also need a `TWILIO_NUMBER`, which you may find [here](https://www.twilio.com/console/phone-numbers/incoming).
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

1. Start the server(`--debug` flag optional, can be used to view request/response log).

   ```bash
   $ ./gradlew jettyRun --debug
   ```

1. Check it out at [http://localhost:8080](http://localhost:8080).

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
