-- CreateEnum
CREATE TYPE "FriendshipStatus" AS ENUM ('PENDING', 'ACCEPTED', 'REJECTED');

-- AlterTable
ALTER TABLE "Blog" ADD COLUMN     "status" "FriendshipStatus" NOT NULL DEFAULT 'PENDING';
