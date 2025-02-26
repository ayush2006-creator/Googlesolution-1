/*
  Warnings:

  - You are about to drop the column `status` on the `Blog` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE "Blog" DROP COLUMN "status";

-- AlterTable
ALTER TABLE "Friendships" ADD COLUMN     "status" "FriendshipStatus" NOT NULL DEFAULT 'PENDING';
