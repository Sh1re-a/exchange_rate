"use client";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
  SelectLabel
} from "@/components/ui/select";
import { Input } from "@/components/ui/input"

import { useState } from "react";

type ConvertResponse = {
  from: string;
  to: string;
  amount: number;
  rate: number;
  result: number;
};

async function callConvertApi(from: string, to: string, amount: number) {
  const qs = new URLSearchParams({
    from,
    to,
    amount: String(amount),
  });
  const res = await fetch(`http://localhost:8080/api/test?${qs.toString()}`, {
    method: "GET",
  });
  return (await res.json()) as ConvertResponse;
}

export default function Home() {
  const [data, setData] = useState<ConvertResponse>();

  const [userInput, setUserInput] = useState({
    from: "",
    to: "",
    amount: 0,
  });

  async function handleConvert() {
    const response = await callConvertApi("EUR", "SEK", 10.0);
    setData(response);
    console.log(response);
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-zinc-50 font-sans dark:bg-black">
      <main className="flex min-h-screen w-full max-w-3xl flex-col items-center justify-between py-32 px-16 bg-white dark:bg-black sm:items-start">
        <div className="flex flex-row gap-5 w-full justify-between">
          <Select>
            <SelectTrigger className="w">
              <SelectValue placeholder="Select a currency" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                <SelectLabel>Valuta</SelectLabel>
                <SelectItem value="EUR" onClick={()=> setUserInput({...userInput, from:"EUR"})}>EUR</SelectItem>
                <SelectItem value="SEK" onClick={()=> setUserInput({...userInput, from:"SEK"})}>SEK</SelectItem>
                <SelectItem value="USD" onClick={()=> setUserInput({...userInput, from:"USD"})}>USD</SelectItem>
              </SelectGroup>
            </SelectContent>
          </Select>
          <Select>
            <SelectTrigger className="w-[180px]">
              <SelectValue placeholder="Select a currency" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                <SelectLabel>Valuta</SelectLabel>
                <SelectItem value="EUR" onClick={()=> setUserInput({...userInput, to:"EUR"})}>EUR</SelectItem>
                <SelectItem value="SEK" onClick={()=> setUserInput({...userInput, to:"SEK"})}>SEK</SelectItem>
                <SelectItem value="USD" onClick={()=> setUserInput({...userInput, to:"USD"})}>USD</SelectItem>
              </SelectGroup>
            </SelectContent>
          </Select>
        </div>
        <Input type="number" placeholder="Amount" className="w-full" value={userInput.amount} onChange={(e) => setUserInput({...userInput, amount: Number(e.target.value)})} />

        <button
          onClick={handleConvert}
          className="rounded bg-blue-500 px-4 py-2 text-white hover:bg-blue-600"
        >
          Convert 10 EUR to SEK
        </button>
        <h1>{JSON.stringify(data)}</h1>
      </main>
    </div>
  );
}
