# Account Verification with Servlets

[![Build Status](https://travis-ci.org/TwilioDevEd/account-verification-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/account-verification-servlets)

Use Authy and Twilio to verify your user's account.

## Local Development

1. Clone this repository and `cd` into its directory:
   ```bash
   $ git git@github.com:TwilioDevEd/account-verification-servlets.git
   $ cd account-verification-servlets
   ```

2. Create the database.
   ```bash
   $ createdb account_verification_servlets
   ```

   _The application uses PostgreSQL as the persistence layer. If you
   don't have it already, you should install it. The easiest way is by
   using [Postgres.app](http://postgresapp.com/)._

3. Edit the sample configuration file `.environment` to match your configuration.

   Once you have edited the `.environment` file, if you are using a UNIX operating system,
   just use the `source` command to load the variables into your environment:

   ```bash
   $ source .environment
   ```

   _If you are using a different operating system, make sure that all the
   variables from the `.environment` file are loaded into your environment._

4. Execute the migrations.
   ```bash
   $ ./gradlew flywayMigrate
   ```

5. Run the application.
   ```bash
   $ ./gradew jettyRun
   ```

6. Check it out at [http://localhost:8080](http://localhost:8080)

That's it!

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
