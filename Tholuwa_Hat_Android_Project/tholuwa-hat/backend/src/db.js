import pg from 'pg';
import { config } from './config.js';
const { Pool } = pg;
export const pool = new Pool({ connectionString: config.databaseUrl, ssl: process.env.PGSSL === 'true' ? { rejectUnauthorized: true } : undefined });
export async function query(text, params=[]) { return pool.query(text, params); }
export async function transaction(fn) {
  const client = await pool.connect();
  try { await client.query('BEGIN'); const result = await fn(client); await client.query('COMMIT'); return result; }
  catch (e) { await client.query('ROLLBACK'); throw e; } finally { client.release(); }
}
