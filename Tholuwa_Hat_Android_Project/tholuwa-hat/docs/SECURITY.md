# Security and launch checklist

- Never commit `.env`, signing keys, API keys, service-account JSON, or database backups.
- Force HTTPS in production; use least-privilege database credentials and restricted CORS origins.
- Configure JWT secret with a cryptographically random value; rotate secrets after suspected exposure.
- SMS provider must enforce delivery limits, abuse controls and phone verification. Development OTP is not production authentication.
- Payment provider integration must verify checkout signatures and webhook signatures on the server, store provider event IDs uniquely, and process idempotently. Never collect/store card data or UPI PIN.
- Enforce ownership and role checks on every write endpoint. Add audit logs for admin financial/moderation actions.
- Use private media buckets, MIME/size checks, image re-encoding, malware review where suitable, and signed URLs.
- Add rate limits per IP/account/phone, spam controls, account deletion/export and backup restore drills.
- Verify applicable food, livestock and other regulated product restrictions with legal counsel. Do not claim seller or product safety verification without actual checks.
- Conduct security review, accessibility review, device QA, and restore/reconciliation tests before production.
