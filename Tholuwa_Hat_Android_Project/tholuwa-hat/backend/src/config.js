import 'dotenv/config';
export const config = {
  port: Number(process.env.PORT || 8080),
  databaseUrl: process.env.DATABASE_URL,
  jwtSecret: process.env.JWT_SECRET,
  nodeEnv: process.env.NODE_ENV || 'development',
  otpMode: process.env.OTP_MODE || 'development',
  otpDevCode: process.env.OTP_DEV_CODE || '123456',
  corsOrigins: (process.env.CORS_ORIGIN || 'http://localhost:5173').split(',').map(s=>s.trim()),
};
if (config.nodeEnv === 'production' && (!config.jwtSecret || config.jwtSecret.length < 32)) throw new Error('JWT_SECRET must be at least 32 chars in production');
