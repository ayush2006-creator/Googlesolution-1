/*
  Warnings:

  - A unique constraint covering the columns `[userId]` on the table `Streak` will be added. If there are existing duplicate values, this will fail.
  - Made the column `lastCheckin` on table `Streak` required. This step will fail if there are existing NULL values in that column.

*/
-- AlterTable
ALTER TABLE "Streak" ALTER COLUMN "lastCheckin" SET NOT NULL;

-- CreateIndex
CREATE UNIQUE INDEX "Streak_userId_key" ON "Streak"("userId");
