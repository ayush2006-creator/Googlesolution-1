/*
  Warnings:

  - You are about to drop the `Milestone` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE "Milestone" DROP CONSTRAINT "Milestone_userId_fkey";

-- DropTable
DROP TABLE "Milestone";

-- CreateTable
CREATE TABLE "Milestones" (
    "id" UUID NOT NULL,
    "userId" UUID NOT NULL,
    "title" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "Milestones_pkey" PRIMARY KEY ("id")
);

-- AddForeignKey
ALTER TABLE "Milestones" ADD CONSTRAINT "Milestones_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User"("id") ON DELETE CASCADE ON UPDATE CASCADE;
